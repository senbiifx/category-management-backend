package com.assessment.categorymanagement.controller;

import com.assessment.categorymanagement.common.dto.ConfirmationResponse;
import com.assessment.categorymanagement.common.dto.Response;
import com.assessment.categorymanagement.common.dto.ResponseStatus;
import com.assessment.categorymanagement.dto.CategoryDto;
import com.assessment.categorymanagement.entity.Category;
import com.assessment.categorymanagement.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = {"Fashion Category Management APIs"})
@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @ApiOperation(value = "Get all categories.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Response with list of categories"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Something went wrong on server's side")
    })
    @GetMapping
    public ResponseEntity<Response<List<CategoryDto>>> getCategories() {
        TypeMap<Category, CategoryDto> typeMap = categoryToDtoMapper();
        List<Category> categories = categoryService.getCategories();
        List<CategoryDto> categoryDtos = categories.stream().map(typeMap::map).collect(Collectors.toList());
        return ResponseEntity.ok(
                Response.<List<CategoryDto>>builder()
                        .status(ResponseStatus.SUCCESS)
                        .data(categoryDtos)
                        .build());
    }

    @ApiOperation(value = "Delete category.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Something went wrong on server's side")
    })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Response<ConfirmationResponse>> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(
                Response.<ConfirmationResponse>builder()
                        .status(ResponseStatus.SUCCESS)
                        .data(new ConfirmationResponse("Category has been deleted successfully."))
                        .build());
    }

    @ApiOperation(value = "Add category.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Something went wrong.")
    })
    @PostMapping
    public ResponseEntity<Response<CategoryDto>> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category parent = Optional.ofNullable(categoryDto.getParentId()).map(Category::new).orElse(null);
        Category category = new Category(null, categoryDto.getName(), parent);
        Category newCategory = categoryService.addCategory(category);

        CategoryDto newCategoryDto = categoryToDtoMapper().map(newCategory);

        return ResponseEntity.ok(
                Response.<CategoryDto>builder()
                        .status(ResponseStatus.SUCCESS)
                        .data(newCategoryDto)
                        .build());
    }

    @ApiOperation(value = "Update category.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful update response"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Something went wrong.")
    })
    @PatchMapping("/{categoryId}")
    public ResponseEntity<Response<ConfirmationResponse>> updateCategory(@PathVariable int categoryId,
                                                                         @Valid
                                                                         @RequestBody CategoryDto categoryDto) {
        Category parent = Optional.ofNullable(categoryDto.getParentId()).map(Category::new).orElse(null);
        Category category = new Category(categoryId, categoryDto.getName(), parent);
        categoryService.updateCategory(categoryId, category);
        return ResponseEntity.ok(
                Response.<ConfirmationResponse>builder()
                        .status(ResponseStatus.SUCCESS)
                        .data(new ConfirmationResponse("Category has been updated successfully."))
                        .build());
    }

    private TypeMap<Category, CategoryDto> categoryToDtoMapper(){
        return modelMapper.typeMap(Category.class, CategoryDto.class)
                        .addMapping(src -> src.getParent().getCategoryId(), CategoryDto::setParentId);
    }
}

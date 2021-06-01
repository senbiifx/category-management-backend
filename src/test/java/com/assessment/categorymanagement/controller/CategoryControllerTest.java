package com.assessment.categorymanagement.controller;

import com.assessment.categorymanagement.common.dto.Response;
import com.assessment.categorymanagement.dto.CategoryDto;
import com.assessment.categorymanagement.entity.Category;
import com.assessment.categorymanagement.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ModelMapper modelMapper;


    @Test
    public void deleteCategory() {
        categoryController.deleteCategory(1);
        verify(categoryService, times(1)).deleteCategory(any());
    }

    @Test
    public void addCategory() {
        categoryController.addCategory(new CategoryDto(null, "sample", 1, null));
        verify(categoryService, times(1)).addCategory(any());
    }

    @Test
    public void updateCategory() {
        categoryController.updateCategory(1, new CategoryDto(null, "sample", 1, null));
        verify(categoryService, times(1)).updateCategory(anyInt(), any());
    }

    @Test
    public void getCategories() {
        when(categoryService.getCategories())
                .thenReturn(Arrays.asList(new Category(2, "Sample", new Category(1))));
        when(modelMapper.typeMap(Category.class, CategoryDto.class))
                .thenReturn(new ModelMapper().typeMap(Category.class, CategoryDto.class));

        ResponseEntity<Response<List<CategoryDto>>> response = categoryController.getCategories();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getData());
        assertFalse(response.getBody().getData().isEmpty());
    }
}
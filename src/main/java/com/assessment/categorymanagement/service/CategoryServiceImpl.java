package com.assessment.categorymanagement.service;

import com.assessment.categorymanagement.exceptions.ErrorCode;
import com.assessment.categorymanagement.exceptions.PreconditionException;
import com.assessment.categorymanagement.entity.Category;
import com.assessment.categorymanagement.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAllByParentNull();
    }

    public void deleteCategory(Integer categoryId){
        categoryRepository.deleteById(categoryId);
    }

    public void addCategory(Category category){
        Optional<Integer> parentId =
                Optional.ofNullable(category)
                        .map(Category::getParent)
                        .map(Category::getCategoryId);
        if(parentId.isPresent()){
            Category parent =
                    categoryRepository
                            .findById(parentId.get())
                            .orElseThrow(() -> new PreconditionException(ErrorCode.INVALID_PARENT));
            category.setParent(parent);
        }
        categoryRepository.save(category);
    }

    public void updateCategory(int id, Category category){
        Category categoryEntity =
                categoryRepository.findById(id).orElseThrow(() -> new PreconditionException(ErrorCode.INVALID_PARENT));
        Optional.ofNullable(category.getName()).ifPresent(categoryEntity::setName);

        Optional<Integer> parentId =
                Optional.ofNullable(category)
                        .map(Category::getParent)
                        .map(Category::getCategoryId);
        if(parentId.isPresent()){
            if(parentId.get() == id){
                log.error("parentId is equal to categoryId during update()");
                throw new PreconditionException(ErrorCode.INVALID_PARENT);
            }
            Category parent =
                    categoryRepository
                            .findById(parentId.get())
                            .orElseThrow(() -> new PreconditionException(ErrorCode.INVALID_PARENT));
            categoryEntity.setParent(parent);
        }

        categoryRepository.save(categoryEntity);
    }
}

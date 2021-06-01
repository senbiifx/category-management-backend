package com.assessment.categorymanagement.service;

import com.assessment.categorymanagement.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    void addCategory(Category category);
    void updateCategory(int id, Category category);
    void deleteCategory(Integer categoryId);
}

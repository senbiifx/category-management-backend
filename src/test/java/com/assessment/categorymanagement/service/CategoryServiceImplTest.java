package com.assessment.categorymanagement.service;

import com.assessment.categorymanagement.common.PreconditionException;
import com.assessment.categorymanagement.entity.Category;
import com.assessment.categorymanagement.repository.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void getCategories() {
        Category category = new Category(1);
        when(categoryRepository.findAllByParentNull())
                .thenReturn(Arrays.asList(category));

        List<Category> list =  categoryService.getCategories();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(category, list.get(0));
    }

    @Test
    public void deleteCategory() {
        categoryService.deleteCategory(1);
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(categoryRepository).deleteById(idCaptor.capture());

        Integer capturedId = idCaptor.getValue();
        assertNotNull(capturedId);
        assertEquals(1, (int)capturedId);
    }

    @Test
    public void addCategory_withoutParent() {
        categoryService.addCategory(new Category());
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    public void addCategory_withParent() {
        when(categoryRepository.findById(any())).thenReturn(Optional.ofNullable(new Category(2)));

        categoryService.addCategory(new Category(null, "Sample", new Category(2)));

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());
        Category capturedCategory = captor.getValue();

        assertNotNull(capturedCategory);
        assertNotNull(capturedCategory.getParent());
        assertEquals(2, (int)capturedCategory.getParent().getCategoryId());
    }

    @Test(expected = PreconditionException.class)
    public void addCategory_withInvalidParent() {
        when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        categoryService.addCategory(new Category(null, "Sample", new Category(2)));
    }

    @Test(expected = PreconditionException.class)
    public void updateCategory_idNotFound() {
        when(categoryRepository.findById(any())).thenReturn(Optional.empty());
        categoryService.updateCategory(1, new Category());
    }

    @Test(expected = PreconditionException.class)
    public void updateCategory_invalidParent_parentIdEqualsCategoryId() {
        when(categoryRepository.findById(any())).thenReturn(Optional.of(new Category(2, "Sample", new Category(1))));
        categoryService.updateCategory(1, new Category(1, "Sample", new Category(1)));
    }

    @Test(expected = PreconditionException.class)
    public void updateCategory_invalidParent_idNotExists() {
        when(categoryRepository.findById(any())).thenReturn(
                Optional.of(new Category(10, "Sample", new Category(12))),
                Optional.empty());

        categoryService.updateCategory(10, new Category(10, "Sample", new Category(12)));
    }

    @Test
    public void updateCategory() {
        when(categoryRepository.findById(any())).thenReturn(
                Optional.of(new Category(10, "Sample", new Category(12))),
                Optional.of(new Category(12, "Sample", new Category(13))));

        categoryService.updateCategory(10, new Category(10, "Sample", new Category(12)));

        verify(categoryRepository, times(1)).save(any());
    }
}
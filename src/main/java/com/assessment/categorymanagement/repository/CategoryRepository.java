package com.assessment.categorymanagement.repository;

import com.assessment.categorymanagement.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     *  Returns all root categories.
     */
    List<Category> findAllByParentNull();

}

package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsCategoryByName(String name);

    Category findCategoryByName(String name);

    Page<Category> findCategoriesByNameStartsWith(String letter, Pageable pageable);

    Page<Category> findCategoriesByName(String name, Pageable pageable);
}

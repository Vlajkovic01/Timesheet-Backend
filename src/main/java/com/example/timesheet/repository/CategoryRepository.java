package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsCategoryByName(String name);

    Category findCategoryByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM category " +
            "WHERE (:searchQuery IS NULL OR category.name LIKE CONCAT(:searchQuery, '%'))")
    Page<Category> filterAll(String searchQuery, Pageable pageable);

    Page<Category> findAll(Pageable pageable);
}

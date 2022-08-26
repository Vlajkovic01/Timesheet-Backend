package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsCategoryByName(String name);

    Category findCategoryByName(String name);

    @Query(value = "SELECT category FROM Category category WHERE category.name LIKE CONCAT('%', :searchQuery, '%')")
    Page<Category> filterAll(String searchQuery, Pageable pageable);

    Page<Category> findAll(Pageable pageable);

    Category findCategoryById(Integer id);
    @Transactional
    @Modifying
    @Query(value = "UPDATE Category category SET category.deleted = true WHERE category.id = :id")
    void deleteCategoryById(@Param("id") Integer id);
}

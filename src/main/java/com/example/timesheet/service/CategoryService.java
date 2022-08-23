package com.example.timesheet.service;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.dto.search.SearchRequestDTO;
import com.example.timesheet.model.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CategoryService {

    Category addNewCategory(CategoryDTO categoryDTO, Authentication authentication);

    boolean existsCategory(String name);

    Category findCategoryByName(String name);

    List<Category> findCategories(SearchRequestDTO searchRequestDTO, Pageable pageable);

    void save(Category category);
}

package com.example.timesheet.service;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.entity.Category;
import org.springframework.security.core.Authentication;

public interface CategoryService {

    Category addNewCategory(CategoryDTO categoryDTO, Authentication authentication);
    boolean existsCategory(String name);

    void save(Category category);
}

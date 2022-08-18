package com.example.timesheet.controller;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.entity.Category;
import com.example.timesheet.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addNewCategory(@Validated @RequestBody CategoryDTO categoryDTO,
                                                   Authentication authentication) {

        Category createdCategory = categoryService.addNewCategory(categoryDTO, authentication);

        if (createdCategory == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(createdCategory, HttpStatus.OK);
    }
}

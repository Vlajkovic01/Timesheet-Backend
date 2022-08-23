package com.example.timesheet.controller;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.dto.search.SearchRequestDTO;
import com.example.timesheet.model.entity.Category;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/category")
public class CategoryController {
    private final CustomModelMapper modelMapper;
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService, CustomModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
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

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getClients(@RequestBody(required = false) SearchRequestDTO searchRequestDTO,
                                                        Pageable pageable) {

        List<Category> categories = categoryService.findCategories(searchRequestDTO, pageable);

        List<CategoryDTO> categoriesDTO = modelMapper.mapAll(categories, CategoryDTO.class);
        return new ResponseEntity<>(categoriesDTO, HttpStatus.OK);
    }
}

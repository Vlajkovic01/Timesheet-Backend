package com.example.timesheet.service.impl;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.dto.search.SearchRequestDTO;
import com.example.timesheet.model.entity.Category;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.CategoryRepository;
import com.example.timesheet.service.CategoryService;
import com.example.timesheet.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CustomModelMapper modelMapper;

    private final EmployeeService employeeService;

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, EmployeeService employeeService, CustomModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Category addNewCategory(CategoryDTO categoryDTO, Authentication authentication) {

        if (!employeeService.isAdmin(authentication)) {
            return null;
        }

        if (existsCategory(categoryDTO.getName())) {
            return null;
        }

        Category newCategory = modelMapper.map(categoryDTO, Category.class);

        save(newCategory);

        return newCategory;
    }

    @Override
    public boolean existsCategory(String name) {
        return categoryRepository.existsCategoryByName(name);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    @Override
    public List<Category> findCategories(SearchRequestDTO searchRequestDTO, Pageable pageable) {
        Page<Category> pagedResult = Page.empty();

        if (searchRequestDTO == null) {
            return categoryRepository.findAll(pageable).getContent();
        }

        if (searchRequestDTO.getSearchFilter() != null) {
            pagedResult = categoryRepository.findCategoriesByNameStartsWith(searchRequestDTO.getSearchFilter(), pageable);
        }

        if (searchRequestDTO.getSearchQuery() != null) {
            pagedResult = categoryRepository.findCategoriesByName(searchRequestDTO.getSearchQuery(), pageable);
        }

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }
}

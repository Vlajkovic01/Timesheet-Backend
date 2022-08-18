package com.example.timesheet.service.impl;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.entity.Category;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.CategoryRepository;
import com.example.timesheet.service.CategoryService;
import com.example.timesheet.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
    public void save(Category category) {
        categoryRepository.save(category);
    }
}

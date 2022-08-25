package com.example.timesheet.service.impl;

import com.example.timesheet.exception.BadRequestException;
import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.entity.Category;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.CategoryRepository;
import com.example.timesheet.service.CategoryService;
import com.example.timesheet.service.EmployeeService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
    public List<Category> findCategories(String searchQuery, Pageable pageable) {
        if (searchQuery == null) {
            return categoryRepository.findAll(pageable).getContent();
        }
        return categoryRepository.filterAll(searchQuery, pageable).getContent();
    }

    @Override
    public Category updateCategory(CategoryDTO categoryDTO) {

        if (categoryDTO == null || categoryDTO.getId() == null) {
            throw new BadRequestException("Please provide all data");
        }

        Category categoryForUpdate = findCategoryById(categoryDTO.getId());
        if (categoryForUpdate == null || existsCategory(categoryDTO.getName())) {
            throw new BadRequestException("Please provide a valid category data");
        }

        categoryForUpdate.setName(categoryDTO.getName());

        save(categoryForUpdate);

        return categoryForUpdate;
    }

    @Override
    public Category findCategoryById(Integer id) {
        return categoryRepository.findCategoryById(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }
}

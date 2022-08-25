package com.example.timesheet.service.impl;

import com.example.timesheet.exception.BadRequestException;
import com.example.timesheet.model.dto.employee.request.EmployeeAddRequestDTO;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.enumeration.Role;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.EmployeeRepository;
import com.example.timesheet.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final CustomModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CustomModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Employee findByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findFirstByEmail(email);
        return employee.orElse(null);
    }

    @Override
    public Employee findCurrentLoggedUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return findByEmail(userDetails.getUsername());
    }

    @Override
    public Employee addNewEmployee(EmployeeAddRequestDTO employeeDTO, Authentication authentication) {
        if (!isAdmin(authentication)) {
            return null;
        }

        Employee newEmployee = modelMapper.map(employeeDTO, Employee.class);
        save(newEmployee);

        return newEmployee;
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        Employee currentLoggedUser = findCurrentLoggedUser(authentication);

        return currentLoggedUser.getRole() == Role.ADMIN;
    }

    @Override
    public Employee updateEmployee(EmployeeAddRequestDTO employeeAddRequestDTO) {
        Employee employee = findByEmail(employeeAddRequestDTO.getEmail());

        if (employee == null) {
            throw new BadRequestException("Please provide a valid Employee data");
        }

        employee.setName(employeeAddRequestDTO.getName());
        employee.setUsername(employeeAddRequestDTO.getUsername());
        employee.setHoursPerWeek(employeeAddRequestDTO.getHoursPerWeek());
        employee.setStatus(employeeAddRequestDTO.getStatus());
        employee.setRole(employeeAddRequestDTO.getRole());

        return save(employee);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

}

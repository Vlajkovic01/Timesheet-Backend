package com.example.timesheet.service.impl;

import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.enumeration.EmployeeStatus;
import com.example.timesheet.model.enumeration.Role;
import com.example.timesheet.repository.EmployeeRepository;
import com.example.timesheet.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findFirstByEmail(email);
        if (!employee.isEmpty()) {
            return employee.get();
        }
        return null;
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
    public boolean isAdmin(Authentication authentication) {
        Employee currentLoggedUser = findCurrentLoggedUser(authentication);

        return currentLoggedUser.getRole() == Role.ADMIN;
    }

}

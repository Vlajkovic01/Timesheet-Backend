package com.example.timesheet.service.impl;

import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.repository.EmployeeRepository;
import com.example.timesheet.service.EmployeeService;
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
    public Employee findByUsername(String username) {
        return null;
    }
}

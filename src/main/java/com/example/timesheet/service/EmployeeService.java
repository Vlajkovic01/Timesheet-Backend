package com.example.timesheet.service;

import com.example.timesheet.model.dto.employee.request.EmployeeAddRequestDTO;
import com.example.timesheet.model.entity.Employee;
import org.springframework.security.core.Authentication;

public interface EmployeeService {

    Employee findByEmail(String email);

    Employee findCurrentLoggedUser(Authentication authentication);

    Employee addNewEmployee(EmployeeAddRequestDTO employeeDTO, Authentication authentication);

    boolean isAdmin(Authentication authentication);

    Employee updateEmployee(EmployeeAddRequestDTO employeeAddRequestDTO);

    Employee save(Employee employee);

    void delete(String email);
}

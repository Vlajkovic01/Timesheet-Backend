package com.example.timesheet.service;

import com.example.timesheet.model.entity.Employee;
import org.springframework.security.core.Authentication;

public interface EmployeeService {

    Employee findByEmail(String email);
    Employee findCurrentLoggedUser(Authentication authentication);
}

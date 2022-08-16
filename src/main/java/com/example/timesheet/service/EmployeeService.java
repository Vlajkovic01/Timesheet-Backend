package com.example.timesheet.service;

import com.example.timesheet.model.entity.Employee;

public interface EmployeeService {

    Employee findByEmail(String email);
    Employee findByUsername(String username);
}

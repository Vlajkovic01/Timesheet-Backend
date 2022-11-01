package com.example.timesheet.service;

import com.example.timesheet.model.dto.employee.request.EmployeeAddRequestDTO;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

    void sendVerificationEmail(EmployeeAddRequestDTO employeeDTO);
}

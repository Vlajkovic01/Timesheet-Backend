package com.example.timesheet.model.dto.employee;

import com.example.timesheet.model.enumeration.EmployeeStatus;
import com.example.timesheet.model.enumeration.Role;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private EmployeeStatus status;
    private Role role;
}

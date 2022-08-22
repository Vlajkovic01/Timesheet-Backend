package com.example.timesheet.model.dto.employee.request;

import com.example.timesheet.model.enumeration.EmployeeStatus;
import com.example.timesheet.model.enumeration.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class EmployeeAddRequestDTO {

    @Length(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    private String name;
    @Length(min = 3, max = 30, message = "Username must be between 3 and 30 characters.")
    private String username;
    @PositiveOrZero(message = "Hours must be positive")
    private Double hoursPerWeek;
    @Email(message = "Please enter a valid email format")
    private String email;
    @NotNull(message = "Status is mandatory")
    private EmployeeStatus status;
    @NotNull(message = "Role is mandatory")
    private Role role;
}

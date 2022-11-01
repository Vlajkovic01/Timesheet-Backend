package com.example.timesheet.model.dto.project;

import com.example.timesheet.model.dto.employee.EmployeeDTO;
import com.example.timesheet.model.enumeration.ProjectStatus;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProjectDTO {

    private Integer id;
    @NotBlank(message = "Name is mandatory")
    @Length(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    private String name;
    @NotBlank(message = "Description is mandatory")
    @Length(min = 3, max = 30, message = "Description must be between 3 and 30 characters.")
    private String description;
    @NotNull(message = "Status is mandatory")
    private ProjectStatus status;
    @NotNull(message = "Lead is mandatory")
    private EmployeeDTO lead;
}

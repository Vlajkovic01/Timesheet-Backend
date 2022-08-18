package com.example.timesheet.model.dto.project;

import com.example.timesheet.model.dto.employee.EmployeeDTO;
import com.example.timesheet.model.enumeration.ProjectStatus;
import lombok.Data;

@Data
public class ProjectDTO {

    private Integer id;
    private String name;
    private String description;
    private ProjectStatus status;
    private EmployeeDTO lead;
}

package com.example.timesheet.model.dto.search;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.dto.employee.EmployeeDTO;
import com.example.timesheet.model.dto.project.ProjectDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportRequestDTO {

    private ClientDTO client;
    private ProjectDTO project;
    private CategoryDTO category;
    private EmployeeDTO employee;
    private LocalDate quickDateWeek;
    private LocalDate quickDateMonth;
    private LocalDate startDate;
    private LocalDate endDate;
}

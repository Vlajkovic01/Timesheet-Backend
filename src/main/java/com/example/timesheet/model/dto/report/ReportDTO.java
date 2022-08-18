package com.example.timesheet.model.dto.report;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.dto.employee.EmployeeDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportDTO {
    private Integer id;
    private LocalDate date;
    private String description;
    private Double hours;
    private Double overtime;
//    private ClientWithoutReportDTO client;
//    private Project project;
    private EmployeeDTO employee;
    private CategoryDTO category;
}

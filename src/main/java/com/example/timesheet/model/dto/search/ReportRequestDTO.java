package com.example.timesheet.model.dto.search;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.dto.employee.EmployeeDTO;
import com.example.timesheet.model.dto.project.ProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequestDTO {

    @NotNull(message = "Client must not be null")
    private ClientDTO client;
    @NotNull(message = "Project must not be null")
    private ProjectDTO project;
    @NotNull(message = "Category must not be null")
    private CategoryDTO category;
    @NotNull(message = "Employee must not be null")
    private EmployeeDTO employee;
    private LocalDate quickDateWeek;
    private LocalDate quickDateMonth;
    private LocalDate startDate;
    private LocalDate endDate;
}

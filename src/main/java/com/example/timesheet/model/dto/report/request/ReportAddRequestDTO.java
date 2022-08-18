package com.example.timesheet.model.dto.report.request;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.dto.project.ProjectDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class ReportAddRequestDTO {
    @NotEmpty(message = "Date is mandatory")
    private LocalDate date;

    @NotBlank(message = "Description is mandatory")
    @Length(min = 3, max = 50, message = "Description must be between 3 and 50 characters.")
    private String description;

    @DecimalMin(value = "7.5", message = "Hours must not be less than 7.5")
    @Max(value = 8, message = "Hours must not be longer than 8")
    private Double hours;

    private Double overtime;
    private CategoryDTO category;
    private ClientDTO client;
    private ProjectDTO project;
}

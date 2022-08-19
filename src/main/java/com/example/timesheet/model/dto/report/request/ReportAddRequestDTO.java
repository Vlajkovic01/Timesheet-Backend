package com.example.timesheet.model.dto.report.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
public class ReportAddRequestDTO {

    private LocalDate date;

    @NotBlank(message = "Description is mandatory")
    @Length(min = 3, max = 50, message = "Description must be between 3 and 50 characters.")
    private String description;

    @PositiveOrZero(message = "Hours must be positive")
    private Double hours;

    @PositiveOrZero(message = "Overtime must be positive")
    private Double overtime;

    @NotBlank(message = "Category name is mandatory")
    private String categoryName;

    @NotBlank(message = "Client name is mandatory")
    private String clientName;

    @NotBlank(message = "Project name is mandatory")
    private String projectName;
}

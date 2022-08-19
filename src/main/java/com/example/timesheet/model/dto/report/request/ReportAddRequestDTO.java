package com.example.timesheet.model.dto.report.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class ReportAddRequestDTO {

    private LocalDate date;

    @NotBlank(message = "Description is mandatory")
    @Length(min = 3, max = 50, message = "Description must be between 3 and 50 characters.")
    private String description;

    @NotEmpty(message = "Hours is mandatory")
    private Double hours;

    private Double overtime;
    private String categoryName;
    private String clientName;
    private String projectName;
}

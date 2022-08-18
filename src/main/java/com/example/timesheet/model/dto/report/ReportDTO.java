package com.example.timesheet.model.dto.report;

import com.example.timesheet.model.dto.category.CategoryDTO;
import com.example.timesheet.model.dto.employee.EmployeeDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class ReportDTO {
    private Integer id;
    @NotEmpty(message = "Date is mandatory")
    private LocalDate date;

    @NotBlank(message = "Description is mandatory")
    @Length(min = 3, max = 50, message = "Description must be between 3 and 50 characters.")
    private String description;

    @DecimalMin(value = "7.5", message = "Hours must not be less than 7.5")
    @Max(value = 8, message = "Hours must not be longer than 8")
    private Double hours;

    private Double overtime;
    private EmployeeDTO employee;
    private CategoryDTO category;
}

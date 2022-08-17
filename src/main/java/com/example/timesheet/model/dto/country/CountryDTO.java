package com.example.timesheet.model.dto.country;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CountryDTO {

    private Integer id;
    @NotBlank(message = "Name is mandatory")
    @Length(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    private String name;
}

package com.example.timesheet.model.dto.client;

import com.example.timesheet.model.dto.country.CountryDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ClientDTO {
    private Integer id;
    @NotBlank(message = "Name is mandatory")
    @Length(min = 3, max = 30, message = "Name must be between 3 and 30 characters.")
    private String name;
    @NotBlank(message = "Address is mandatory")
    @Length(min = 3, max = 30, message = "Address must be between 3 and 30 characters.")
    private String address;
    @NotBlank(message = "City is mandatory")
    @Length(min = 3, max = 30, message = "City must be between 3 and 30 characters.")
    private String city;
    @NotNull(message = "Zip is mandatory")
    private Integer zip;
    private CountryDTO country;
}

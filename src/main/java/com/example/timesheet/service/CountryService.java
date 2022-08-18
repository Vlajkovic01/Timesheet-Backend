package com.example.timesheet.service;

import com.example.timesheet.model.entity.Country;

public interface CountryService {
    Country findCountryByName(String name);
}

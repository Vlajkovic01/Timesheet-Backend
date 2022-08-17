package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Country findCountryByName(String name);
}

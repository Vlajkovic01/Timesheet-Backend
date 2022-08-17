package com.example.timesheet.service.impl;

import com.example.timesheet.model.entity.Country;
import com.example.timesheet.repository.CountryRepository;
import com.example.timesheet.service.CountryService;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country findCountryByName(String name) {
        return countryRepository.findCountryByName(name);
    }
}

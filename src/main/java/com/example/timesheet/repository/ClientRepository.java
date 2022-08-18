package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsClientByNameAndAddressAndCityAndZipAndCountry(String name, String address, String city, Integer zip, Country country);
}

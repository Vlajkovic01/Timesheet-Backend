package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsProjectByNameAndClient(String name, Client client);
    Project findProjectByNameAndClient(String name, Client client);
}

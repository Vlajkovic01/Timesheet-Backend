package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsProjectByNameAndClient(String name, Client client);
    Project findProjectByNameAndClient(String name, Client client);
    Page<Project> findProjectsByNameStartsWith(String letter, Pageable pageable);

    Page<Project> findProjectsByName(String name, Pageable pageable);
}

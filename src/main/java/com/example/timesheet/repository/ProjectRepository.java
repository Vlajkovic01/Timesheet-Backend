package com.example.timesheet.repository;

import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsProjectByNameAndClient(String name, Client client);

    Project findProjectByNameAndClient(String name, Client client);

    Project findProjectById(Integer id);

    @Query(value = "SELECT project FROM Project project WHERE project.name LIKE CONCAT('%', :searchQuery, '%')")
    Page<Project> filterAll(String searchQuery, Pageable pageable);

    Page<Project> findAll(Pageable pageable);

    Project findProjectByName(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Project project SET project.deleted = true WHERE project.id = :id")
    void deleteProjectById(@Param("id") Integer id);
}

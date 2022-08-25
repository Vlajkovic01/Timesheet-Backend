package com.example.timesheet.service;

import com.example.timesheet.model.dto.project.request.ProjectAddRequestDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ProjectService {
    Project addNewProject(ProjectAddRequestDTO projectAddRequestDTO, Authentication authentication);

    boolean existsProjectByNameAndClient(String name, Client client);

    Project findProjectByNameAndClient(String name, Client client);

    List<Project> findProjects(String searchQuery, Pageable pageable);

    void save(Project project);
}

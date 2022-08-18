package com.example.timesheet.service;

import com.example.timesheet.model.dto.project.request.ProjectAddRequestDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.entity.Project;
import org.springframework.security.core.Authentication;

public interface ProjectService {
    Project addNewProject(ProjectAddRequestDTO projectAddRequestDTO, Authentication authentication);

    boolean existsProjectByNameAndClient(String name, Client client);
    void save(Project project);
}

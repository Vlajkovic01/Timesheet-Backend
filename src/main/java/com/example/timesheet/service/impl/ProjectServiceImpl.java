package com.example.timesheet.service.impl;

import com.example.timesheet.model.dto.project.request.ProjectAddRequestDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.entity.Project;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.ProjectRepository;
import com.example.timesheet.service.ClientService;
import com.example.timesheet.service.EmployeeService;
import com.example.timesheet.service.ProjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ClientService clientService;

    private final CustomModelMapper modelMapper;
    private final EmployeeService employeeService;

    public ProjectServiceImpl(EmployeeService employeeService, CustomModelMapper modelMapper, ClientService clientService, ProjectRepository projectRepository) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
        this.clientService = clientService;
        this.projectRepository = projectRepository;
    }

    @Override
    public Project addNewProject(ProjectAddRequestDTO projectAddRequestDTO, Authentication authentication) {

        if (!employeeService.isAdmin(authentication)) {
            return null;
        }

        Client client = clientService.findClientByName(projectAddRequestDTO.getClientName());
        if (client == null) {
            return null;
        }

        if (existsProjectByNameAndClient(projectAddRequestDTO.getName(), client)) {
            return null;
        }

        Employee lead = employeeService.findByEmail(projectAddRequestDTO.getLead().getEmail());
        if (lead == null) {
            return null;
        }

        Project newProject = modelMapper.map(projectAddRequestDTO, Project.class);
        newProject.setClient(client);
        newProject.setLead(lead);

        save(newProject);

        return newProject;
    }

    @Override
    public boolean existsProjectByNameAndClient(String name, Client client) {
        return projectRepository.existsProjectByNameAndClient(name, client);
    }

    @Override
    public Project findProjectByNameAndClient(String name, Client client) {
        return projectRepository.findProjectByNameAndClient(name, client);
    }

    @Override
    public List<Project> findProjects(String searchQuery, Pageable pageable) {
        if (searchQuery == null) {
            return projectRepository.findAll(pageable).getContent();
        }
        return projectRepository.filterAll(searchQuery, pageable).getContent();
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }
}

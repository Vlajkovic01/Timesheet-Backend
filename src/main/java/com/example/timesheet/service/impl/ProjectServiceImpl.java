package com.example.timesheet.service.impl;

import com.example.timesheet.exception.BadRequestException;
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
    public Project findProjectById(Integer id) {
        return projectRepository.findProjectById(id);
    }

    @Override
    public List<Project> findProjects(String searchQuery, Pageable pageable) {
        if (searchQuery == null) {
            return projectRepository.findAll(pageable).getContent();
        }
        return projectRepository.filterAll(searchQuery, pageable).getContent();
    }

    @Override
    public Project updateProject(ProjectAddRequestDTO projectAddRequestDTO) {

        Project projectForUpdate = findProjectByName(projectAddRequestDTO.getName());
        Client client = clientService.findClientByName(projectAddRequestDTO.getClientName());
        Employee lead = employeeService.findByEmail(projectAddRequestDTO.getLead().getEmail());

        if (projectForUpdate == null || client == null || lead == null) {
            throw new BadRequestException("Please provide a valid data");
        }

        projectForUpdate.setDescription(projectAddRequestDTO.getDescription());
        projectForUpdate.setClient(client);
        projectForUpdate.setLead(lead);
        projectForUpdate.setStatus(projectAddRequestDTO.getStatus());

        save(projectForUpdate);

        return projectForUpdate;
    }

    @Override
    public Project findProjectByName(String name) {
        return projectRepository.findProjectByName(name);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void delete(Integer id) {
        Project projectForDelete = findProjectById(id);

        if (projectForDelete == null) {
            throw new BadRequestException("Please provide a valid Project data.");
        }

        projectRepository.deleteProjectById(projectForDelete.getId());
    }
}

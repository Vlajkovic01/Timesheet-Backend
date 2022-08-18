package com.example.timesheet.controller;

import com.example.timesheet.model.dto.project.ProjectDTO;
import com.example.timesheet.model.dto.project.request.ProjectAddRequestDTO;
import com.example.timesheet.model.entity.Project;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/project")
public class ProjectController {

    private final CustomModelMapper modelMapper;
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService, CustomModelMapper modelMapper) {
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProjectDTO> addNewProject(@Validated @RequestBody ProjectAddRequestDTO projectAddRequestDTO,
                                                    Authentication authentication) {

        Project createdProject = projectService.addNewProject(projectAddRequestDTO, authentication);

        if (createdProject == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProjectAddRequestDTO projectDTO = modelMapper.map(createdProject, ProjectAddRequestDTO.class);
        return new ResponseEntity<>(projectDTO, HttpStatus.OK);
    }
}
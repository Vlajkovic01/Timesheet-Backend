package com.example.timesheet.controller;

import com.example.timesheet.exception.BadRequestException;
import com.example.timesheet.model.dto.client.response.ClientResponseDTO;
import com.example.timesheet.model.dto.project.ProjectDTO;
import com.example.timesheet.model.dto.project.request.ProjectAddRequestDTO;
import com.example.timesheet.model.dto.project.response.ProjectResponseDTO;
import com.example.timesheet.model.entity.Project;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.service.ProjectService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getProjects(@RequestParam(required = false, name = "name") String searchQuery,
                                                                Pageable pageable) {

        List<Project> projects = projectService.findUndeletedProjects(searchQuery, pageable);

        List<ProjectResponseDTO> projectsDTO = modelMapper.mapAll(projects, ProjectResponseDTO.class);
        return new ResponseEntity<>(projectsDTO, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProjectResponseDTO> updateProject(@Validated @RequestBody ProjectAddRequestDTO projectAddRequestDTO) {

        try {
            Project updatedProject = projectService.updateProject(projectAddRequestDTO);
            ProjectResponseDTO projectDTO = modelMapper.map(updatedProject, ProjectResponseDTO.class);

            return new ResponseEntity<>(projectDTO, HttpStatus.OK);
        } catch (BadRequestException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProject(@RequestBody Integer id) {
        try {
            projectService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException exception) {
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}

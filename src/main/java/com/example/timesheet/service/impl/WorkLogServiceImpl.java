package com.example.timesheet.service.impl;

import com.example.timesheet.model.dto.search.ReportRequestDTO;
import com.example.timesheet.model.dto.worklog.request.WorkLogAddRequestDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.entity.WorkLog;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.WorkLogRepository;
import com.example.timesheet.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class WorkLogServiceImpl implements WorkLogService {
    private static final double MIN_HOURS_PER_DAY = 7.5;
    private final CategoryService categoryService;
    private final ProjectService projectService;
    private final ClientService clientService;
    private final CustomModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final WorkLogRepository workLogRepository;

    public WorkLogServiceImpl(WorkLogRepository workLogRepository, EmployeeService employeeService, CustomModelMapper modelMapper, ClientService clientService, ProjectService projectService, CategoryService categoryService) {
        this.workLogRepository = workLogRepository;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
        this.clientService = clientService;
        this.projectService = projectService;
        this.categoryService = categoryService;
    }

    @Override
    public List<WorkLog> addNewWorkLogs(List<WorkLogAddRequestDTO> workLogsDTO, Authentication authentication) {

        Employee employee = employeeService.findCurrentLoggedUser(authentication);

        List<WorkLog> workLogs = workLogsDTO.stream().map(logDTO -> {
            Client client = clientService.findClientByName(logDTO.getClientName());
            WorkLog newWorkLog = modelMapper.map(logDTO, WorkLog.class);
            newWorkLog.setEmployee(employee);
            newWorkLog.setClient(client);
            newWorkLog.setProject(projectService.findProjectByNameAndClient(logDTO.getProjectName(), client));
            newWorkLog.setCategory(categoryService.findCategoryByName(logDTO.getCategoryName()));

            return newWorkLog;
        }).toList();

        if (!validateWorkedHours(workLogs, MIN_HOURS_PER_DAY)) {
            return Collections.emptyList();
        }

        return saveAll(workLogs);
    }
    @Override
    public WorkLog save(WorkLog workLog) {
        try {
            return workLogRepository.save(workLog);
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public List<WorkLog> saveAll(List<WorkLog> workLogs) {
        try {
            return workLogRepository.saveAll(workLogs);
        } catch (Exception exception) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean validateWorkedHours(List<WorkLog> workLogs, double minRequirementHours) {
        double workedHours = 0;

        for (WorkLog workLog : workLogs) {
            workedHours += workLog.getHours();
        }

        return workedHours >= minRequirementHours;
    }

    @Override
    public List<WorkLog> findWorkLogs(ReportRequestDTO reportRequestDTO, Pageable pageable) {
        Page<WorkLog> pagedResult = Page.empty();

        if (reportRequestDTO == null) {
            return pagedResult.getContent();
        }
         pagedResult = workLogRepository.filterAll(reportRequestDTO, pageable);

        return pagedResult.getContent();
    }
}

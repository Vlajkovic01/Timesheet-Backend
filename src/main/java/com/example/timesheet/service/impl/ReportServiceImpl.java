package com.example.timesheet.service.impl;

import com.example.timesheet.model.dto.report.request.ReportAddRequestDTO;
import com.example.timesheet.model.entity.Client;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.entity.Report;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.ReportRepository;
import com.example.timesheet.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private static final double MIN_HOURS_PER_DAY = 7.5;
    private final CategoryService categoryService;
    private final ProjectService projectService;
    private final ClientService clientService;
    private final CustomModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository, EmployeeService employeeService, CustomModelMapper modelMapper, ClientService clientService, ProjectService projectService, CategoryService categoryService) {
        this.reportRepository = reportRepository;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
        this.clientService = clientService;
        this.projectService = projectService;
        this.categoryService = categoryService;
    }

    //TODO change name from 'Report' to 'Worklog'
    @Override
    public List<Report> addNewReports(List<ReportAddRequestDTO> reportsDTO, Authentication authentication) {

        Employee employee = employeeService.findCurrentLoggedUser(authentication);

        List<Report> reports = reportsDTO.stream().map(reportDTO -> {
            Client client = clientService.findClientByName(reportDTO.getClientName());
            Report newReport = modelMapper.map(reportDTO, Report.class);
            newReport.setEmployee(employee);
            newReport.setClient(client);
            newReport.setProject(projectService.findProjectByNameAndClient(reportDTO.getProjectName(), client));
            newReport.setCategory(categoryService.findCategoryByName(reportDTO.getCategoryName()));

            return newReport;
        }).toList();

        if (!validateWorkedHours(reports, MIN_HOURS_PER_DAY)) {
            return Collections.emptyList();
        }

        return saveAll(reports);
    }
    @Override
    public Report save(Report report) {
        try {
            return reportRepository.save(report);
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public List<Report> saveAll(List<Report> reports) {
        try {
            return reportRepository.saveAll(reports);
        } catch (Exception exception) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean validateWorkedHours(List<Report> reports, double minRequirementHours) {
        double workedHours = 0;

        for (Report report : reports) {
            workedHours += report.getHours();
        }

        return workedHours >= minRequirementHours;
    }
}

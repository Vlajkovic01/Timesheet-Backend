package com.example.timesheet.service.impl;

import com.example.timesheet.model.dto.report.request.ReportAddRequestDTO;
import com.example.timesheet.model.entity.*;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.repository.ReportRepository;
import com.example.timesheet.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportServiceImpl implements ReportService {
    private static final Double MAX_HOURS_PER_DAY = 7.5;
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

    @Override
    public Report addNewReport(ReportAddRequestDTO reportAddRequestDTO, Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        Employee employee = employeeService.findCurrentLoggedUser(authentication);
        if (employee == null) {
            return null;
        }

        Client client = clientService.findClientByName(reportAddRequestDTO.getClientName());
        if (client == null) {
            return null;
        }

        Project project = projectService.findProjectByNameAndClient(reportAddRequestDTO.getProjectName(), client);
        if (project == null) {
            return null;
        }

        Category category = categoryService.findCategoryByName(reportAddRequestDTO.getCategoryName());
        if (category == null) {
            return null;
        }

        if (isSumOfHoursGreaterThanHoursPerDay(MAX_HOURS_PER_DAY, reportAddRequestDTO.getDate(), employee)) {
            return null;
        }

        Report newReport = modelMapper.map(reportAddRequestDTO, Report.class);
        newReport.setEmployee(employee);
        newReport.setClient(client);
        newReport.setProject(project);
        newReport.setCategory(category);

        save(newReport);
        return newReport;
    }

    @Override
    public boolean isSumOfHoursGreaterThanHoursPerDay(Double hoursPerDay, LocalDate date, Employee employee) {
        return reportRepository.isSumOfHoursGreaterThanHoursPerDay(hoursPerDay, date, employee.getId());
    }

    @Override
    public void save(Report report) {
        reportRepository.save(report);
    }
}

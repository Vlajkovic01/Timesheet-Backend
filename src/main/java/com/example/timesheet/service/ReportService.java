package com.example.timesheet.service;

import com.example.timesheet.model.dto.report.request.ReportAddRequestDTO;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.entity.Report;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

public interface ReportService {

    Report addNewReport(ReportAddRequestDTO reportAddRequestDTO, Authentication authentication);
    boolean isSumOfHoursGreaterThanHoursPerDay(Double hoursPerDay, LocalDate date, Employee employee);
    void save(Report report);
}

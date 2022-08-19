package com.example.timesheet.service;

import com.example.timesheet.model.dto.report.request.ReportAddRequestDTO;
import com.example.timesheet.model.entity.Report;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReportService {

    List<Report> addNewReports(List<ReportAddRequestDTO> reportsDTO, Authentication authentication);

    Report save(Report report);

    List<Report> saveAll(List<Report> reports);

    boolean validateWorkedHours(List<Report> reports, double minRequirementHours);
}

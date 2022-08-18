package com.example.timesheet.service;

import com.example.timesheet.model.dto.report.request.ReportAddRequestDTO;
import com.example.timesheet.model.entity.Report;
import org.springframework.security.core.Authentication;

public interface ReportService {

    Report addNewReport(ReportAddRequestDTO reportAddRequestDTO, Authentication authentication);
    void save(Report report);
}

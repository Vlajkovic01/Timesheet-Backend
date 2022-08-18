package com.example.timesheet.service.impl;

import com.example.timesheet.model.dto.report.request.ReportAddRequestDTO;
import com.example.timesheet.model.entity.Report;
import com.example.timesheet.repository.ReportRepository;
import com.example.timesheet.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report addNewReport(ReportAddRequestDTO reportAddRequestDTO, Authentication authentication) {
        return null;
    }

    @Override
    public void save(Report report) {
        reportRepository.save(report);
    }
}

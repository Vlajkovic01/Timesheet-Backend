package com.example.timesheet.controller;

import com.example.timesheet.model.dto.report.ReportDTO;
import com.example.timesheet.model.dto.report.request.ReportAddRequestDTO;
import com.example.timesheet.model.entity.Report;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/report")
@Validated
public class ReportController {

    private final CustomModelMapper modelMapper;
    private final ReportService reportService;

    public ReportController(ReportService reportService, CustomModelMapper modelMapper) {
        this.reportService = reportService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public ResponseEntity<List<ReportDTO>> addNewReport(@RequestBody List<@Valid ReportAddRequestDTO> reportsAddRequestDTO,
                                                        Authentication authentication) {

        List<Report> createdReports = reportService.addNewReports(reportsAddRequestDTO, authentication);

        if (createdReports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<ReportDTO> reportsDTO = modelMapper.mapAll(createdReports, ReportDTO.class);
        return new ResponseEntity<>(reportsDTO, HttpStatus.OK);
    }
}

package com.example.timesheet.controller;

import com.example.timesheet.model.dto.worklog.WorkLogDTO;
import com.example.timesheet.model.dto.worklog.request.WorkLogAddRequestDTO;
import com.example.timesheet.model.entity.WorkLog;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.service.WorkLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/worklog")
public class WorkLogController {

    private final CustomModelMapper modelMapper;
    private final WorkLogService workLogService;

    public WorkLogController(WorkLogService workLogService, CustomModelMapper modelMapper) {
        this.workLogService = workLogService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public ResponseEntity<List<WorkLogDTO>> addNewWorkLog(@RequestBody List<WorkLogAddRequestDTO> workLogsAddRequestDTO,
                                                          Authentication authentication) {

        List<WorkLog> createdWorkLogs = workLogService.addNewWorkLogs(workLogsAddRequestDTO, authentication);

        if (createdWorkLogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<WorkLogDTO> workLogsDTO = modelMapper.mapAll(createdWorkLogs, WorkLogDTO.class);
        return new ResponseEntity<>(workLogsDTO, HttpStatus.OK);
    }
}
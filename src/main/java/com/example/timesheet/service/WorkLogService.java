package com.example.timesheet.service;

import com.example.timesheet.model.dto.worklog.request.WorkLogAddRequestDTO;
import com.example.timesheet.model.entity.WorkLog;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WorkLogService {

    List<WorkLog> addNewWorkLogs(List<WorkLogAddRequestDTO> workLogsDTO, Authentication authentication);

    WorkLog save(WorkLog workLog);

    List<WorkLog> saveAll(List<WorkLog> workLogs);

    boolean validateWorkedHours(List<WorkLog> workLogs, double minRequirementHours);
}

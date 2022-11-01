package com.example.timesheet.model.dto.client.response;

import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.dto.project.ProjectDTO;
import com.example.timesheet.model.dto.worklog.WorkLogDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientResponseDTO extends ClientDTO {

    private Set<WorkLogDTO> workLogs = new HashSet<>();
    private Set<ProjectDTO> projects = new HashSet<>();
}

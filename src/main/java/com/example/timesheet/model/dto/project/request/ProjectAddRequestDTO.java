package com.example.timesheet.model.dto.project.request;

import com.example.timesheet.model.dto.project.ProjectDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectAddRequestDTO extends ProjectDTO {
    private String clientName;
}

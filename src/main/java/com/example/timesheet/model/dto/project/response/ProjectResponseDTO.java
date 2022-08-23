package com.example.timesheet.model.dto.project.response;

import com.example.timesheet.model.dto.client.ClientDTO;
import com.example.timesheet.model.dto.project.ProjectDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectResponseDTO extends ProjectDTO {
    private ClientDTO client;
}

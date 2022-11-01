package com.example.timesheet.model.dto.project.request;

import com.example.timesheet.model.dto.project.ProjectDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectAddRequestDTO extends ProjectDTO {
    @NotNull(message = "Client is mandatory")
    private String clientName;
}

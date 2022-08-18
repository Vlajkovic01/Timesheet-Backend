package com.example.timesheet.model.dto.report.response;

import com.example.timesheet.model.dto.project.ProjectDTO;
import com.example.timesheet.model.dto.report.ReportDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReportWithoutClientDTO extends ReportDTO {

    private ProjectDTO project;
}

package com.example.timesheet.model.dto.worklog.response;

import com.example.timesheet.model.dto.project.ProjectDTO;
import com.example.timesheet.model.dto.worklog.WorkLogDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WorkLogWithoutClientDTO extends WorkLogDTO {

    private ProjectDTO project;
}

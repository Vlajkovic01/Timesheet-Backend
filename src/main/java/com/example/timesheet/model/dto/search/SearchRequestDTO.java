package com.example.timesheet.model.dto.search;

import lombok.Data;

@Data
public class SearchRequestDTO {

    private String searchQuery;
    private String searchFilter;
}

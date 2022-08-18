package com.example.timesheet.model.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenState {
    private String accessToken;
    private Long expiresIn;
}

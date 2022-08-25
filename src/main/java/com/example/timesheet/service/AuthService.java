package com.example.timesheet.service;

import com.example.timesheet.model.dto.auth.request.ChangePasswordRequestDTO;
import org.springframework.security.core.Authentication;

public interface AuthService {
    void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, Authentication authentication);
}

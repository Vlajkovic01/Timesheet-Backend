package com.example.timesheet.service;

import com.example.timesheet.model.dto.auth.request.ChangePasswordRequestDTO;
import com.example.timesheet.model.dto.auth.request.SignupVerificationDTO;
import org.springframework.security.core.Authentication;

public interface AuthService {
    void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, Authentication authentication);

    void signupVerification(SignupVerificationDTO signupVerificationDTO);
}

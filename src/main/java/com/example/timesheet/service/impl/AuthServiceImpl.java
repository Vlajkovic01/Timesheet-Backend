package com.example.timesheet.service.impl;

import com.example.timesheet.exception.BadRequestException;
import com.example.timesheet.exception.PasswordMatchException;
import com.example.timesheet.model.dto.auth.request.ChangePasswordRequestDTO;
import com.example.timesheet.model.dto.auth.request.SignupVerificationDTO;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.security.TokenUtils;
import com.example.timesheet.service.AuthService;
import com.example.timesheet.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final TokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;

    public AuthServiceImpl(EmployeeService employeeService, PasswordEncoder passwordEncoder, TokenUtils tokenUtils) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public void changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, Authentication authentication) {
        Employee currentLoggedUser = employeeService.findCurrentLoggedUser(authentication);

        if (currentLoggedUser == null) {
            throw new BadRequestException("Please provide a valid user");
        }

        if (passwordEncoder.matches(changePasswordRequestDTO.getCurrentPassword(), currentLoggedUser.getPassword()) &&
                changePasswordRequestDTO.getNewPassword().equals(changePasswordRequestDTO.getConfirmNewPassword())) {

            currentLoggedUser.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
            employeeService.save(currentLoggedUser);
        } else {
            throw new PasswordMatchException("Password must match");
        }
    }

    @Override
    public void signupVerification(SignupVerificationDTO signupVerificationDTO) {
        Employee employee = employeeService.findByEmail(tokenUtils.getEmailFromToken(signupVerificationDTO.getToken()));

        if (tokenUtils.isTokenExpired(signupVerificationDTO.getToken()) || employee == null) {
            throw new BadRequestException("Token is invalid");
        }

        if (signupVerificationDTO.getNewPassword().equals(signupVerificationDTO.getConfirmPassword())) {
            employee.setPassword(passwordEncoder.encode(signupVerificationDTO.getNewPassword()));
            employeeService.save(employee);
        } else {
            throw new PasswordMatchException("Password must match");
        }
    }
}

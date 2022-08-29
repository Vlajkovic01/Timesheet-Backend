package com.example.timesheet.controller;

import com.example.timesheet.exception.BadRequestException;
import com.example.timesheet.exception.PasswordMatchException;
import com.example.timesheet.model.dto.auth.request.ChangePasswordRequestDTO;
import com.example.timesheet.model.dto.auth.request.LoginRequestDTO;
import com.example.timesheet.model.dto.auth.request.SignupVerificationDTO;
import com.example.timesheet.model.dto.auth.response.UserTokenState;
import com.example.timesheet.model.dto.employee.EmployeeDTO;
import com.example.timesheet.model.dto.employee.request.EmployeeAddRequestDTO;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.security.TokenUtils;
import com.example.timesheet.service.AuthService;
import com.example.timesheet.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    private final AuthService authService;
    private final CustomModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public AuthController(TokenUtils tokenUtils, AuthenticationManager authenticationManager, EmployeeService employeeService, CustomModelMapper modelMapper, AuthService authService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken
            (@Validated @RequestBody LoginRequestDTO employeeLoginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                employeeLoginRequestDTO.getEmail(), employeeLoginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getAuthorities().toArray()[0].toString());
        long expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping(value = "/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Validated EmployeeAddRequestDTO employeeDTO, Authentication authentication) {

        Employee createdEmployee = employeeService.addNewEmployee(employeeDTO, authentication);

        if (createdEmployee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        EmployeeDTO newEmployeeDTO = modelMapper.map(createdEmployee, EmployeeDTO.class);
        return new ResponseEntity<>(newEmployeeDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/change-password")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_WORKER')")
    public ResponseEntity<String> changePassword(@RequestBody @Validated ChangePasswordRequestDTO changePasswordDTO, Authentication authentication) {

        try {
            authService.changePassword(changePasswordDTO, authentication);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException | PasswordMatchException exception) {
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/verify-account")
    public ResponseEntity<String> signupVerification(@RequestBody @Validated SignupVerificationDTO signupVerificationDTO) {

        try {
            authService.signupVerification(signupVerificationDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException | PasswordMatchException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

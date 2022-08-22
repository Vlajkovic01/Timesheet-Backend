package com.example.timesheet.controller;

import com.example.timesheet.model.dto.auth.request.LoginRequestDTO;
import com.example.timesheet.model.dto.auth.response.UserTokenState;
import com.example.timesheet.model.dto.employee.EmployeeDTO;
import com.example.timesheet.model.dto.employee.request.EmployeeAddRequestDTO;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.model.mapper.CustomModelMapper;
import com.example.timesheet.security.TokenUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    private final CustomModelMapper modelMapper;
    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public AuthController(TokenUtils tokenUtils, AuthenticationManager authenticationManager, EmployeeService employeeService, CustomModelMapper modelMapper) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken
            (@Validated @RequestBody LoginRequestDTO employeeLoginRequestDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                employeeLoginRequestDTO.getEmail(), employeeLoginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
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

}

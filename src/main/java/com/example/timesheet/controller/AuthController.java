package com.example.timesheet.controller;

import com.example.timesheet.model.dto.auth.request.LoginRequestDTO;
import com.example.timesheet.model.dto.auth.response.UserTokenState;
import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.security.TokenUtils;
import com.example.timesheet.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final EmployeeService employeeService;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public AuthController(TokenUtils tokenUtils, AuthenticationManager authenticationManager, EmployeeService employeeService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken
            (@Validated @RequestBody LoginRequestDTO employeeLoginRequestDTO) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                employeeLoginRequestDTO.getEmail(), employeeLoginRequestDTO.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }


    @PostMapping(value = "/test")
    public ResponseEntity<Employee> test(Authentication authentication) {
        //TODO this is only for test auth login, delete later after review
        Employee currentLoggedUser = employeeService.findCurrentLoggedUser(authentication);

        if (currentLoggedUser == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(currentLoggedUser, HttpStatus.OK);
    }
}

package com.example.timesheet.controller;

import com.example.timesheet.model.dto.auth.request.LoginRequestDTO;
import com.example.timesheet.model.dto.auth.response.UserTokenState;
import com.example.timesheet.security.TokenUtils;
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

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public AuthController(TokenUtils tokenUtils, AuthenticationManager authenticationManager) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
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

}

package com.example.timesheet.service.impl;

import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.service.CustomUserDetailsService;
import com.example.timesheet.service.EmployeeService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final EmployeeService employeeService;

    public CustomUserDetailsServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeService.findByEmail(username);

        if(employee == null){
            throw new UsernameNotFoundException("There is no user with email " + username);
        }else{
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            String role = "ROLE_" + employee.getRole().toString();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));

            return User.withUsername(employee.getEmail().trim()).password(employee.getPassword().trim()).authorities(grantedAuthorities).build();
        }
    }

    @Override
    public UserDetails loadEmployeeByEmail(String email) {
        Employee employee = employeeService.findByEmail(email);

        if(employee == null){
            throw new UsernameNotFoundException("There is no user with email " + email);
        }else{
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            String role = "ROLE_" + employee.getRole().toString();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));

            return User.withUsername(employee.getEmail().trim()).password(employee.getPassword().trim()).authorities(grantedAuthorities).build();
        }
    }
}

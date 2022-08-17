package com.example.timesheet.service.impl;

import com.example.timesheet.model.entity.Employee;
import com.example.timesheet.service.EmployeeService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeService employeeService;

    public UserDetailsServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeService.findByEmail(username);

        if (employee == null) {
            throw new UsernameNotFoundException("There is no employee with email " + username);
        } else {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            String role = "ROLE_" + employee.getRole().toString();
            grantedAuthorities.add(new SimpleGrantedAuthority(role));

            return new org.springframework.security.core.userdetails.User(
                    employee.getEmail().trim(),
                    employee.getPassword().trim(),
                    grantedAuthorities);
        }
    }
}

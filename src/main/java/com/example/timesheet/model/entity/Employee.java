package com.example.timesheet.model.entity;

import com.example.timesheet.model.enumeration.EmployeeStatus;
import com.example.timesheet.model.enumeration.Role;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column
    private String name;
    private String username;
    private String password;
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

}

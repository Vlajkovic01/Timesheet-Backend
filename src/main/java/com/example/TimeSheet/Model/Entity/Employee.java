package com.example.TimeSheet.Model.Entity;

import com.example.TimeSheet.Model.Enum.EmployeeStatus;
import com.example.TimeSheet.Model.Enum.Role;
import lombok.*;

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

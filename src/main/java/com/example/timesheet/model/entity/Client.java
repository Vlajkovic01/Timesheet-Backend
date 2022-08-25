package com.example.timesheet.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column
    private String name;
    private String address;
    private String city;
    private Integer zip;
    private String country;


    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<WorkLog> workLogs = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Project> projects = new HashSet<>();
}

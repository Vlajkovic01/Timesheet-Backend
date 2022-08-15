package com.example.TimeSheet.Model.Entity;

import lombok.Data;

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

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "client")
    private Set<Report> reports = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private Set<Project> projects = new HashSet<>();
}

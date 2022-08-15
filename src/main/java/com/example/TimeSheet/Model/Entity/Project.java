package com.example.TimeSheet.Model.Entity;

import com.example.TimeSheet.Model.Enum.ProjectStatus;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column
    private String name;
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee lead;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}

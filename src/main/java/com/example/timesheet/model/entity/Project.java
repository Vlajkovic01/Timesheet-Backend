package com.example.timesheet.model.entity;

import com.example.timesheet.model.enumeration.ProjectStatus;
import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "client")
@ToString(exclude = "client")
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

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted;
}

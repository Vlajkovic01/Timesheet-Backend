package com.example.TimeSheet.Model.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String name;
}

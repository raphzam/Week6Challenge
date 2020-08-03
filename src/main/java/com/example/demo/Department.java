package com.example.demo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToMany (mappedBy = "department")
    private Set<User> employees;


}

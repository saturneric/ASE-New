package com.codesdream.ase.model;

import javax.persistence.*;

@Entity
@Table(name = "user_tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
}

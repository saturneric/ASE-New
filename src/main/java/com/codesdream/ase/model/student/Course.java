package com.codesdream.ase.model.student;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    String name;

    float credit;
}

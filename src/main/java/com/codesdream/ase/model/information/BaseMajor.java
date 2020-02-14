package com.codesdream.ase.model.information;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "base_major")
public class BaseMajor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
}

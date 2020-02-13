package com.codesdream.ase.model.information;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "base_ethnic")
public class BaseEthnic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
}
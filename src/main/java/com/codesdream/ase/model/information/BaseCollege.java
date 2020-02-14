package com.codesdream.ase.model.information;

import lombok.Data;

import javax.persistence.*;

/**
 * 学院基本信息
 */
@Data
@Entity
@Table(name = "base_college")
public class BaseCollege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
}

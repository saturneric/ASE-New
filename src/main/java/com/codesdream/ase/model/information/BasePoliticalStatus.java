package com.codesdream.ase.model.information;

import lombok.Data;

import javax.persistence.*;

/**
 * 政治面貌
 */
@Data
@Entity
@Table(name = "base_political_status")
public class BasePoliticalStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private  String name;
}

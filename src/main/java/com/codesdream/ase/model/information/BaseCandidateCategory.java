package com.codesdream.ase.model.information;

import lombok.Data;

import javax.persistence.*;

/**
 * 考生类别
 */
@Data
@Entity
@Table(name = "base_candidate_category")
public class BaseCandidateCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

}

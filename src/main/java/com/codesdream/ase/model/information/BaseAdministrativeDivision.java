package com.codesdream.ase.model.information;

import lombok.Data;

import javax.persistence.*;

/**
 * 行政区划基本信息
 */
@Data
@Entity
@Table(name = "base_administrative_division")
public class BaseAdministrativeDivision {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private int parent_id;
}

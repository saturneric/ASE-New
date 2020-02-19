package com.codesdream.ase.model.information;

import lombok.Data;

import javax.persistence.*;

/**
 * 政治面貌基本信息
 */
@Data
@Entity
@Table(name = "base_political_status")
public class BasePoliticalStatus {
    @Id
    private int id;

    private  String name;
}

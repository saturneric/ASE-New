package com.codesdream.ase.model.information;

import lombok.Data;

import javax.persistence.*;

/**
 * 专业基本信息
 */
@Data
@Entity
@Table(name = "base_major")
public class BaseMajor {
    @Id
    private String id;

    private String name;
}

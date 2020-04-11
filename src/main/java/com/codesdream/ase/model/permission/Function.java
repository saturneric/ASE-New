package com.codesdream.ase.model.permission;

import io.swagger.models.auth.In;
import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

/**
 * 功能
 */
@Data
@Entity
@Table(name = "function")
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // 功能名称
    private String name;

    // 功能描述
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Function father = null;

    // 授权url
    private String url;
}

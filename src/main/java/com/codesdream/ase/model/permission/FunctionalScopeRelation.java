package com.codesdream.ase.model.permission;


import lombok.Data;

import javax.persistence.*;

/**
 * 功能性权限容器与范围性权限容器关联对
 */
@Data
@Entity
@Table(name = "functional_scope_relation")
public class FunctionalScopeRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 对应功能性权限容器
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private FunctionalPermissionContainer functionalPermissionContainer;

    // 对应范围性权限容器
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private ScopePermissionContainer scopePermissionContainer;
}

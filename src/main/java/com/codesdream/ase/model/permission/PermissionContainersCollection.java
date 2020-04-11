package com.codesdream.ase.model.permission;

import lombok.Data;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限容器集合（多项权力）
 */
@Data
@Entity
@Table(name = "permission_container_collection")
public class PermissionContainersCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 权限容器集合名称
    @Column(unique = true)
    private String name = "";

    // 权限容器集合概述
    private String description = "";

    // 对应功能性权限容器与范围性权限容器关联对
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FunctionalScopeRelation> functionalScopeRelations = new HashSet<>();

    public PermissionContainersCollection(String name){
        this.name = name;
    }

    public PermissionContainersCollection(){

    }
}

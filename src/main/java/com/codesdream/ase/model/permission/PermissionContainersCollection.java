package com.codesdream.ase.model.permission;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

/**
 * 权限容器集合
 */
@Data
@Entity
@Table(name = "permission_container_collection")
public class PermissionContainersCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 对应功能性权限容器与范围性权限容器关联对
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FunctionalScopeRelation> functionalScopeRelations;
}

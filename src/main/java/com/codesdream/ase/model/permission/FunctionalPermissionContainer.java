package com.codesdream.ase.model.permission;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


/**
 * 功能性权限容器
 */
@Data
@Entity
@Table(name = "functional_permit_container")
public class FunctionalPermissionContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 功能性权限容器名
    @Column(unique = true)
    private String name = "";

    // 功能性权限容器解释
    private String description = "";

    // 对应访问控制角色列表
    @ElementCollection
    @Column(nullable = false)
    private List<String> roles = new LinkedList<>();

    // 是否启用
    @Column(nullable = false)
    private boolean enabled = true;

    // 是否删除
    @Column(nullable = false)
    private boolean deleted = false;

    public FunctionalPermissionContainer(String name) {
        this.name = name;
    }

    public FunctionalPermissionContainer(){

    }

}

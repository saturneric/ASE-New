package com.codesdream.ase.model.permission;

import lombok.Data;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 标签
 */
@Data
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 标签名
    @Column(unique = true)
    private String name = "";

    // 标签解释
    private String description = "";

    // 标签关联用户
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    // 启用标志
    @Column(nullable = false)
    private boolean enabled = true;

    // 删除标志
    @Column(nullable = false)
    private boolean deleted = false;

    // 对应权限容器集合
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<PermissionContainersCollection> permissionContainersCollections = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    public Tag(){

    }

}

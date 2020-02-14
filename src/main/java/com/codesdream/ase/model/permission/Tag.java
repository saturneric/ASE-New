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
    @Column(nullable = false, unique = true)
    private String name;

    // 标签解释
    private String description;

    // 标签关联用户
    @ManyToMany(mappedBy = "tags", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<User> users;

    // 启用标志
    @Column(nullable = false)
    private boolean enabled;

    // 删除标志
    @Column(nullable = false)
    private boolean deleted;

    // 对应权限容器集合
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PermissionContainersCollection> permissionContainersCollections;

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
        this.deleted = false;
        this.enabled = true;
        this.users = new HashSet<User>();
    }

    public Tag() {
        this.deleted = false;
        this.enabled = true;
        this.users = new HashSet<User>();
    }

}

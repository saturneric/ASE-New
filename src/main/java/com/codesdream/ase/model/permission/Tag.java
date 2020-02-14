package com.codesdream.ase.model.permission;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 标签
 */
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

    // 标签对应权限容器
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "tags")
    private Set<FunctionalPermissionContainer> functionalPermissionContainers;

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
        this.deleted = false;
        this.enabled = true;

        this.users = new HashSet<User>();
        this.functionalPermissionContainers = new HashSet<FunctionalPermissionContainer>();
    }

    public Tag() {
        this.deleted = false;
        this.enabled = true;

        this.users = new HashSet<User>();
        this.functionalPermissionContainers = new HashSet<FunctionalPermissionContainer>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<FunctionalPermissionContainer> getFunctionalPermissionContainers() {
        return functionalPermissionContainers;
    }

    public void setFunctionalPermissionContainers(Set<FunctionalPermissionContainer> functionalPermissionContainers) {
        this.functionalPermissionContainers = functionalPermissionContainers;
    }
}

package com.codesdream.ase.model.permission;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "functional_permit_container")
public class FunctionalPermissionContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 功能性权限容器名
    @Column(nullable = false, unique = true)
    private String name;

    // 功能性权限容器解释
    private String description;

    // 功能性容器对应范围性容器
    @ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
    private ScopePermissionContainer scopePermissionContainer;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<Tag> tags;

    @ElementCollection
    @Column(nullable = false)
    private List<String> urls;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean deleted;

    public FunctionalPermissionContainer(String name, String description) {
        this.name = name;
        this.description = description;

        this.tags = new HashSet<Tag>();
        this.scopePermissionContainer = null;
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

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}

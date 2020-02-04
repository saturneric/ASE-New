package com.codesdream.ase.model.pernission;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "functional_permit_container")
public class FunctionalPermissionContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 功能性权限容器名
    // @Column(nullable = false, unique = true)
    private String name;

    // 功能性权限容器解释
    private String description;

    @ElementCollection
    @Column(nullable = false)
    private List<String> urls;

    // @Column(nullable = false)
    private boolean enabled;

    // @Column(nullable = false)
    private boolean deleted;

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

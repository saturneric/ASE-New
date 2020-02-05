package com.codesdream.ase.model.permission;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "scope_permit_container")
public class ScopePermissionContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @OneToMany(cascade=CascadeType.MERGE,fetch=FetchType.LAZY,mappedBy="scopePermissionContainer")
    private Set<FunctionalPermissionContainer> functionalPermissionContainers;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
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
}

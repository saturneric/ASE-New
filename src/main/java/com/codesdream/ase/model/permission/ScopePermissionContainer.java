package com.codesdream.ase.model.permission;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "scope_permit_container")
public class ScopePermissionContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String name = "";

    private String description = "";

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private List<Tag> tags = new LinkedList<>();

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean deleted = false;

    public ScopePermissionContainer(String name){
        this.name = name;
    }

    public ScopePermissionContainer(){

    }
}

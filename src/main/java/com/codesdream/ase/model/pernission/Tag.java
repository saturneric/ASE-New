package com.codesdream.ase.model.pernission;

import javax.persistence.*;
import java.util.Objects;

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

    // 启用标志
    @Column(nullable = false)
    private boolean enabled;

    // 删除标志
    @Column(nullable = false)
    private boolean deleted;

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
        this.deleted = false;
        this.enabled = true;
    }

    public Tag() {
        this.deleted = false;
        this.enabled = true;
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
}

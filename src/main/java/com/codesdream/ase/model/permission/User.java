package com.codesdream.ase.model.permission;

import com.codesdream.ase.component.UserRolesListGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    // 用户名
    @Column(unique = true, nullable = false)
    private String username;

    // 密码（必须以哈希值sha256储存）
    @Column(nullable = false)
    private String password;

    // 手机号
    @Column(nullable = true)
    private String phone_number;

    // 用户关联标签
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_tag",
            joinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "tag_id", referencedColumnName = "id")
            })
    private Set<Tag> tags;

    // 是否启用
    @Column(nullable =  false)
    private boolean enabled;
    // 是否删除
    @Column(nullable = false)
    private boolean deleted;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        this.deleted = false;
        this.tags = new HashSet<Tag>();
    }

    public User() {
        this.username = null;
        this.password = null;
        this.enabled = true;
        this.deleted = false;
        this.tags = new HashSet<Tag>();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRolesListGenerator userRolesListGenerator = new UserRolesListGenerator();
        return userRolesListGenerator.GenerateRoles(this);
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}

package com.codesdream.ase.model.permission;

import com.codesdream.ase.component.UserRolesListGenerator;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
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

    // 账号是否过期
    private boolean accountNonExpired;

    // 账号是否被封禁
    private boolean accountNonLocked;

    // 证书是否过期
    private boolean credentialsNonExpired;

    // 账号是否激活
    private  boolean enabled;

    // 是否删除
    @Column(nullable = false)
    private boolean deleted;

    // 用户关联标签
    @ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.LAZY)
    private Set<Tag> tags;



    public User(String username, String password) {
        this.username = username;
        this.password = password;

        initUserDefault();
    }

    public User() {
        this.username = null;
        this.password = null;
        this.deleted = false;

        initUserDefault();
    }

    // 用默认的方式初始化User对象的值
    private void initUserDefault(){
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.deleted = false;
        this.tags = new HashSet<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRolesListGenerator userRolesListGenerator = new UserRolesListGenerator();
        return userRolesListGenerator.generateRoles(this);
    }

}

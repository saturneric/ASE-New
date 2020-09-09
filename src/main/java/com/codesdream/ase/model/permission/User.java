package com.codesdream.ase.model.permission;

import com.codesdream.ase.model.file.Image;
import com.codesdream.ase.model.mark.Tag;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

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

    // 访问控制角色(不在数据表中) Spring Security
    private transient Collection<?extends GrantedAuthority> authorities;

    // 用户详细信息
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserDetail userDetail;

    // 用户认证表
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserAuth userAuth;

    @ManyToMany(cascade = CascadeType.MERGE)
    List<Tag> tags;


    public User(String username, String password) {
        this.username = username;
        this.password = password;

        initDefault();
    }

    public User() {
        this.username = null;
        this.password = null;
        this.deleted = false;

        initDefault();
    }

    // 用默认的方式初始化User对象的值
    private void initDefault(){
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.deleted = false;
        this.enabled = true;
        this.userDetail = new UserDetail();
        this.userAuth = new UserAuth();
        this.authorities = new ArrayList<>();
    }

}
package com.codesdream.ase.component;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class UserRolesListGenerator {
    @Resource
    UserRepository userRepository;

    public Collection<GrantedAuthority> generateRoles(String username){
        Optional<User> user = userRepository.findByUsername(username);
        // 如果没有找到用户
        if(!user.isPresent()) throw new RuntimeException("User Not Found");

        return generateRoles(user.get());
    }

    public Collection<GrantedAuthority> generateRoles(User user){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 列举用户对应的所有标签
        for(Tag tag : user.getTags()){
            // 列举标签对应的所有功能性权限容器
            // 等待添加
        }
        return authorities;
    }
}

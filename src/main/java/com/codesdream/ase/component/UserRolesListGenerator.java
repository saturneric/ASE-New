package com.codesdream.ase.component;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserRolesListGenerator {
    public Collection<GrantedAuthority> GenerateRoles(User user){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(Tag tag : user.getTags()){
            for(FunctionalPermissionContainer functionalPermissionContainer : tag.getFunctionalPermissionContainers()){
                authorities.add(new SimpleGrantedAuthority(functionalPermissionContainer.getName()));
            }
        }
        return authorities;
    }
}

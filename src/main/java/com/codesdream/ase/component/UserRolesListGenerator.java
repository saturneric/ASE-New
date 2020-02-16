package com.codesdream.ase.component;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 生成用户访问权限角色列表
 */
@Component
public class UserRolesListGenerator {

    private UserFunctionalPermissionContainersListGenerator functionalPermissionContainersListGenerator;

    public UserRolesListGenerator(
            UserFunctionalPermissionContainersListGenerator userFunctionalPermissionContainersListGenerator)
    {
        this.functionalPermissionContainersListGenerator =
                userFunctionalPermissionContainersListGenerator;
    }

    public Collection<GrantedAuthority> generateRoles(
            Collection<FunctionalPermissionContainer> functionalPermissionContainers){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(FunctionalPermissionContainer functionalPermissionContainer :functionalPermissionContainers){
            for(String role :functionalPermissionContainer.getRoles()){
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }

    public Collection<GrantedAuthority> generateRoles(String username){
        return generateRoles(
                functionalPermissionContainersListGenerator.generateFunctionalPermissionContainers(username)
        );
    }

    public Collection<GrantedAuthority> generateRoles(User user){

        return generateRoles(
                functionalPermissionContainersListGenerator.generateFunctionalPermissionContainers(user)
        );
    }
}

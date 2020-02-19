package com.codesdream.ase.component.permission;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 生成用户访问权限角色列表
 */
@Component
public class UserRolesListGenerator {
    @Resource
    private UserFPCListGenerator fpcListGenerator;

    public Collection<GrantedAuthority> generateRoles(
            Collection<FunctionalPermissionContainer> fpcs){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(FunctionalPermissionContainer fpc :fpcs){
            for(String role :fpc.getRoles()){
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }

    public Collection<GrantedAuthority> generateRoles(String username){
        return generateRoles(
                fpcListGenerator.generateFPCs(username)
        );
    }

    public Collection<GrantedAuthority> generateRoles(User user){

        return generateRoles(
                fpcListGenerator.generateFPCs(user)
        );
    }
}

package com.codesdream.ase.component;

import com.codesdream.ase.model.permission.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class UserAuthoritiesGenerator {
    @Resource
    UserRolesListGenerator userRolesListGenerator;

    public Collection<? extends GrantedAuthority> grantedAuthorities(User user){
        return userRolesListGenerator.generateRoles(user);
    }

}

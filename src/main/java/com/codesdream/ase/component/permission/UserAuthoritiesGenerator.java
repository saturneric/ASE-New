package com.codesdream.ase.component.permission;

import com.codesdream.ase.model.permission.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

@Component
public class UserAuthoritiesGenerator {
    @Resource
    UserFunctionsListGenerator userFunctionsListGenerator;

    public Collection<? extends GrantedAuthority> grantedAuthorities(User user){
        return userFunctionsListGenerator.generateRoles(user);
    }

}

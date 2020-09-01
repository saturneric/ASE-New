package com.codesdream.ase.component.permission;

import com.codesdream.ase.model.permission.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class UserAuthoritiesGenerator {

    public Collection<? extends GrantedAuthority> grantedAuthorities(User user){
        return new ArrayList<>();
    }

}

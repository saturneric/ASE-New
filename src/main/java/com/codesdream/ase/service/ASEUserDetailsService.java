package com.codesdream.ase.service;

import com.codesdream.ase.component.UserAuthoritiesGenerator;
import com.codesdream.ase.model.permission.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ASEUserDetailsService implements UserDetailsService {

    @Resource
    IUserService userService;

    @Resource
    UserAuthoritiesGenerator userAuthoritiesGenerator;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(s);
        user.setAuthorities(userAuthoritiesGenerator.grantedAuthorities(user));
        return user;
    }
}

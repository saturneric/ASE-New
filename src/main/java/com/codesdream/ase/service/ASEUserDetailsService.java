package com.codesdream.ase.service;

import com.codesdream.ase.component.permission.UserAuthoritiesGenerator;
import com.codesdream.ase.exception.UserNotFoundException;
import com.codesdream.ase.model.permission.User;
import org.springframework.security.authentication.AuthenticationServiceException;
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
    public UserDetails loadUserByUsername(String s) {
        try {
            Optional<User> userOptional = userService.findUserByUsername(s);
            if(!userOptional.isPresent()) throw new UserNotFoundException(s);
            User user = userOptional.get();
            user.setAuthorities(userAuthoritiesGenerator.grantedAuthorities(user));
            return user;
        } catch (UserNotFoundException e){
            throw  new AuthenticationServiceException("User Not Exist");
        }

    }
}

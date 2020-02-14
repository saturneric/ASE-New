package com.codesdream.ase.service;

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
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("User: " + s);
        Optional<User> user = userService.findUserByUsername(s);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("User Not Found");
        }
        else {
            System.out.println("Returning user information");
            System.out.println("User Password: "+user.get().getPassword());
            return user.get();
        }
    }
}

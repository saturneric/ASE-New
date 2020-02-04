package com.codesdream.ase.service;

import com.codesdream.ase.model.pernission.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
public class ASEUserDetailsService implements UserDetailsService {

    @Resource
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(!userService.findUserByUsername(s).isPresent()){
            throw new UsernameNotFoundException("用户不存在");
        }
        else {
            return userService.findUserByUsername(s).get();
        }
    }
}

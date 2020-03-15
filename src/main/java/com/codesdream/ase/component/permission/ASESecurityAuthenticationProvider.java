package com.codesdream.ase.component.permission;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

// 普通用户名密码验证, 用户获得Token
@Slf4j
@Component
public class ASESecurityAuthenticationProvider implements AuthenticationProvider {
    @Resource
    UserDetailsService userDetailsService;

    @Resource
    ASEUsernameEncoder usernameEncoder;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获得登录表单中的学号
        String username = usernameEncoder.encode(authentication.getName());
        // 获得表单中的密码
        String password = passwordEncoder.encode(authentication.getCredentials().toString());
        // 判断用户是否存在
        UserDetails userInfo = userDetailsService.loadUserByUsername(username);

        log.info(String.format("SecurityAuthentication: %s %s", username, password));

        if (userInfo == null) {
            throw new UsernameNotFoundException("User IS Not Existing");
        }

        // 判断密码是否正确
        if (!userInfo.getPassword().equals(password)) {
            throw new BadCredentialsException("Password IS Uncorrected");
        }

        // 判断账号是否停用/删除
        if (!userInfo.isEnabled()) {
            throw new DisabledException("User IS Disabled");
        }
        else if(!userInfo.isAccountNonLocked()){
            throw new LockedException("User IS Locked");
        }
        else if(!userInfo.isAccountNonExpired()){
            throw new  AccountExpiredException("User IS Expired");
        }

        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

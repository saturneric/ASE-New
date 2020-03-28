package com.codesdream.ase.component.activity;

import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.permission.UserRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

//获取当前用户的用户名
@Component
public class CurrentUserGetter {
    private Optional<User> user;
    public Optional<User> getCurrentUser(HttpServletRequest request){

        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        ASESpringUtil aseSpringUtil = new ASESpringUtil();
        UserRepository userRepository = aseSpringUtil.getBean(UserRepository.class);
        this.user = userRepository.findByUsername(username);
        return this.user;
    }

}

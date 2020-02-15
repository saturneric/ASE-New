package com.codesdream.ase.service;

import com.codesdream.ase.component.ASEPasswordEncoder;
import com.codesdream.ase.component.UserRolesListGenerator;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Resource
    UserRolesListGenerator userRolesListGenerator;

    @Resource
    UserRepository userRepository;

    @Resource
    ASEPasswordEncoder asePasswordEncoder;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getUserAuthorities(User user) {
        return userRolesListGenerator.generateRoles(user);
    }

    @Override
    public void updatePassword(User user, String password) {
        user.setPassword(asePasswordEncoder.encode(password));
        update(user);
    }

    @Override
    public User save(User user) {
        // 查找用户名是否已经被注册
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new RuntimeException("Username Already Exists");
        user.setPassword(asePasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if(!userRepository.findByUsername(user.getUsername()).isPresent())
            throw new RuntimeException("Username Already Exists");
        return userRepository.save(user);

    }

    // 获得一个默认初始化的用户对象
    @Override
    public User getDefaultUser() {
        return new User();
    }

}

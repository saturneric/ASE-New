package com.codesdream.ase.service;

import com.codesdream.ase.component.ASEPasswordEncoder;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
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
    public User save(User user) {
        user.setPassword(asePasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setDeleted(false);
        return userRepository.save(user);
    }


}
package com.codesdream.ase.service;

import com.codesdream.ase.model.permission.User;

import java.util.List;
import java.util.Optional;


public interface IUserService {
   List<User> findAll();

   Optional<User> findUserById(int id);
   Optional<User> findUserByUsername(String username);
   User save(User user);
   // 获得默认用户
   User getDefaultUser();

}

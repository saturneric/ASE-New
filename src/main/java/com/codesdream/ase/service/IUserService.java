package com.codesdream.ase.service;

import com.codesdream.ase.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
   List<User> findAll();

   Optional<User> findUserById(long id);
   Optional<User> findUserByUsername(String username);
   User save(User user);
}

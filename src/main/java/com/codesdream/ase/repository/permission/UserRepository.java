package com.codesdream.ase.repository.permission;

import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}

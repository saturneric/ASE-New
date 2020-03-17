package com.codesdream.ase.repository.activity;

import com.codesdream.ase.model.activity.UserActivity;
import com.codesdream.ase.model.permission.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserActivityRepository extends CrudRepository<UserActivity, Integer> {

    UserActivity findByUser(User user);
}

package com.codesdream.ase.repository.activity;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.permission.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findByTitle(String title);
    List<Activity> findByTitle(String title, Sort sort);

    List<Activity> findByCreator(User creator);
    List<Activity> findByCreator(User creator, Sort sort);

    List<Activity> findByManager(User manager);
    List<Activity> findByManager(User manager, Sort sort);

}

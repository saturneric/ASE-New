package com.codesdream.ase.repository.activity;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.permission.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    Optional<Activity> findActivityByTitleAndType(String title,String type);

    Optional<Activity> findActivityByTitle(String title);

    Optional<Activity> findByCreator(String creatorName);

}
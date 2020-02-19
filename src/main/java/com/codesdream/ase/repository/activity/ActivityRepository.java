package com.codesdream.ase.repository.activity;

import com.codesdream.ase.model.activity.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer> {

    Optional<Activity> findByTitle(String Title);

    Optional<Activity> findByCreator(String creatorName);

}
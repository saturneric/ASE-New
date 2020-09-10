package com.codesdream.ase.repository.activity;

import com.codesdream.ase.model.activity.Activity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    List<Activity> findByTitle(String title);
    List<Activity> findByTitle(String title, Sort sort);

    List<Activity> findByCreatorId(int creator);
    List<Activity> findByCreatorId(int creator, Sort sort);

}

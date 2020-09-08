package com.codesdream.ase.repository.activity;

import com.codesdream.ase.model.activity.Report;
import com.codesdream.ase.model.permission.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    Optional<Report> findByActivityId(int activityId);

    List<Report> findByTitle(String title);
    List<Report> findByTitle(String title, Sort sort);

    List<Report> findByCreator(User creator);
    List<Report> findByCreator(User creator, Sort sort);

    List<Report> findByManager(User manager);
    List<Report> findByManager(User manager, Sort sort);

}

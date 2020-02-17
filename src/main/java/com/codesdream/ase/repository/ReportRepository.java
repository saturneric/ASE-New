package com.codesdream.ase.repository;

import com.codesdream.ase.model.activity.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {
    Optional<Report> findByTitle(String reportTitle);

    Optional<Report> findByCreator(String creatorName);
}

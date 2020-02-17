package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.Report;

import java.util.Optional;

public interface IReportService {

    Optional<Report> findByTitle(String title);

    Optional<Report> findByCreator(String creatorName);

    Report save(Activity activity, Report report);

    void delete(Report report);

    Report update (Report report);
}

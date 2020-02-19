package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.Report;
import com.codesdream.ase.repository.activity.ReportRepository;

import javax.annotation.Resource;
import java.util.Optional;

public class ReportService implements IReportService {

    @Resource
    ReportRepository reportRepository;
    ActivityService activityService;

    @Override
    public Optional<Report> findByTitle(String title) {
        return reportRepository.findByTitle(title);
    }

    @Override
    public Optional<Report> findByCreator(String creatorName) {
        return reportRepository.findByCreator(creatorName);
    }

    @Override
    public Report save(Activity activity, Report report) {
        if(activity == null){
            throw new RuntimeException("Activity does not exist.");
        }
        activityService.addReport(activity, report);
        return reportRepository.save(report);
    }

    @Override
    public void delete(Report report) {
        if(reportRepository.findById(report.getId()).isPresent()) reportRepository.delete(report);
        else throw new RuntimeException("Report does not exist.");
    }

    @Override
    public Report update(Report report) {
        reportRepository.save(report);
        return report;
    }
}

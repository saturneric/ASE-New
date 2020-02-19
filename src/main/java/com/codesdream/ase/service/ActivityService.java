package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.Report;
import com.codesdream.ase.repository.activity.ActivityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class ActivityService implements IActivityService {

    @Resource
    private ActivityRepository activityRepository;

    @Override
    public Optional<Activity> findActivityByTitle(String title) {
        return activityRepository.findByTitle(title);
    }

    @Override
    public Optional<Activity> findActivityByCreator(String creatorName) {
        return activityRepository.findByCreator(creatorName);
    }

    @Override
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity addReport(Activity activity, Report report) {
       activity.setReport(report);
       return update(activity);
    }

    @Override
    public void delete(Activity activity) {
        activityRepository.delete(activity);
    }

    @Override
    public Activity update(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

}

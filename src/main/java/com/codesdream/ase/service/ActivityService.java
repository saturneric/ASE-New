package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.Period;
import com.codesdream.ase.model.activity.Report;
import com.codesdream.ase.model.activity.UserActivity;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.activity.ActivityRepository;
import com.codesdream.ase.repository.activity.UserActivityRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ActivityService implements IActivityService {

    @Resource
    private ActivityRepository activityRepository;

    @Resource
    private UserActivityRepository userActivityRepository;

    @Override
    public Optional<Activity> findActivityByTitle(String title) {
        return activityRepository.findByTitle(title);
    }

    @Override
    public Optional<Activity> findActivityByCreator(String creatorName) {
        return activityRepository.findByCreator(creatorName);
    }


    @Override
    public List<Activity> findActivityByType(User user, String type) {
        return activityRepository.findByUserAndType(user, type);
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

    @Override
    public List<Activity> findMainResponsibleActs(User user) {

        UserActivity userActivity = userActivityRepository.findByUser(user);
        List<Activity> acts = userActivity.getMainResponsibleActs();
        Collections.sort(acts, new ActivityComparator());
        return acts;
    }

    @Override
    public List<Activity> findSecondaryResponsibleActs(User user) {

        UserActivity userActivity = userActivityRepository.findByUser(user);
        List<Activity> acts = userActivity.getSecondaryResponsibleActs();
        Collections.sort(acts, new ActivityComparator());
        return acts;
    }

    @Override
    public List<Activity> findVisibleActs(User user) {

        UserActivity userActivity = userActivityRepository.findByUser(user);
        List<Activity> acts = userActivity.getVisibleActs();
        Collections.sort(acts, new ActivityComparator());
        return acts;
    }

    @Override
    public List<Activity> findSignActs(User user) {

        UserActivity userActivity = userActivityRepository.findByUser(user);
        List<Activity> acts = userActivity.getSignUpActs();
        Collections.sort(acts, new ActivityComparator());
        return acts;
    }

    @Override
    public List<Activity> findParticipatedActs(User user) {

        UserActivity userActivity = userActivityRepository.findByUser(user);
        List<Activity> acts = userActivity.getParticipatedActs();
        Collections.sort(acts, new ActivityComparator());
        return acts;
    }

    @Override
    public List<Activity> findParticipatingActs(User user) {

        UserActivity userActivity = userActivityRepository.findByUser(user);
        List<Activity> acts = userActivity.getParticipatingActs();
        Collections.sort(acts, new ActivityComparator());
        return acts;
    }

    @Override
    public List<Activity> findCreatedActs(User user) {

        UserActivity userActivity = userActivityRepository.findByUser(user);
        List<Activity> acts = userActivity.getCreatedActs();
        Collections.sort(acts, new ActivityComparator());
        return acts;
    }

    @Override
    public List<Activity> findAll(User user) {

        List<Activity> activities = findCreatedActs(user);
        List<Activity> activities1 = findMainResponsibleActs(user);
        List<Activity> activities2 = findSecondaryResponsibleActs(user);
        List<Activity> activities3 = findParticipatingActs(user);
        List<Activity> activities4 = findParticipatedActs(user);
        List<Activity> activities5 = findParticipatingActs(user);
        List<Activity> activities6 = findSignActs(user);
        List<Activity> activities7 = findVisibleActs(user);
        Set<Activity> activitySet = new HashSet<>();
        activitySet.addAll(activities);
        activitySet.addAll(activities1);
        activitySet.addAll(activities2);
        activitySet.addAll(activities3);
        activitySet.addAll(activities4);
        activitySet.addAll(activities5);
        activitySet.addAll(activities6);
        activitySet.addAll(activities7);
        List<Activity> res = new ArrayList<>(activitySet);
        Collections.sort(res, new ActivityComparator());
        return res;
    }


}

class ActivityComparator implements Comparator<Activity> {


    @Override
    public int compare(Activity o1, Activity o2) {

        Period p1 = o1.getRealPeriod();
        Period p2 = o2.getRealPeriod();
        LocalDateTime s1 = p1.getStartTime();
        LocalDateTime s2 = p2.getStartTime();
        if (s1.isBefore(s2)) return -1;
        else if (s1.isAfter(s2)) return 1;
        else return 0;
    }
}
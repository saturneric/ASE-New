package com.codesdream.ase.service.activity;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.activity.ActivityRepository;
import com.codesdream.ase.repository.permission.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class ActivityService {

    @Resource
    ActivityRepository activityRepository;

    @Resource
    UserRepository userRepository;

    public Activity addMember(int activityId, int memberId, boolean type){

        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        Optional<User> optionalUser = userRepository.findById(memberId);
        if(!optionalActivity.isPresent() || !optionalUser.isPresent()){
            return null;
        }
        Activity activity = optionalActivity.get();
        User user = optionalUser.get();
        if(!type){
            activity.getManager().add(user);
        }
        else{
            activity.getParticipantIds().add(user.getId());
        }
        return activityRepository.save(activity);
    }



}

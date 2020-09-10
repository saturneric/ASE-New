package com.codesdream.ase.service;

import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.message.Message;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.activity.ActivityRepository;
import com.codesdream.ase.repository.permission.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Resource
    ActivityRepository activityRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    MessageService messageService;

    /**
     * 为指定活动添加指定成员
     * @param activityId 活动id
     * @param memberId 成员id
     * @param type 成员类型，false表示管理员，true表示普通参与者
     * @return 更新之后的活动
     */
    public Activity addMember(int activityId, int memberId, boolean type){

        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        Optional<User> optionalUser = userRepository.findById(memberId);
        if(!optionalActivity.isPresent() || !optionalUser.isPresent()){
            return null;
        }
        Activity activity = optionalActivity.get();
        User user = optionalUser.get();
        if(!type){
            activity.getManagerIds().add(user.getId());
        }
        else{
            activity.getParticipantIds().add(user.getId());
        }
        return activityRepository.save(activity);
    }

    /**
     * 删除给定活动的指定参与者
     * @param memberId 参与者id
     * @param activityId 活动id
     * @param type 参与者类型，0表示管理员，1表示普通参与者
     * @return 是否删除成功
     */
    public boolean removeMember(int memberId, int activityId, int type){

        Optional<User> optionalUser = userRepository.findById(memberId);
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        try{
            if(!optionalActivity.isPresent() || !optionalUser.isPresent()){
                throw new NotFoundException("No such activity or user.");
            }
        } catch (Exception e){
            return false;
        }

        Activity activity = optionalActivity.get();
        if(type == 0){
            try{
               if(!activity.getManagerIds().contains(memberId)){
                    throw new NotFoundException("No such person managed this activity.");
                }
            }catch (Exception e){
                return false;
            }
            activity.getManagerIds().remove(memberId);
        } else{
            try{
                if(!activity.getParticipantIds().contains(memberId)){
                    throw new NotFoundException("No such person participated in this activity.");
                }
            }catch (Exception e){
                return false;
            }
            activity.getParticipantIds().remove(memberId);

        }

        activityRepository.save(activity);
        return true;
    }

    /**
     * 为活动相关人员推送DDL提醒
     * @Todo
     * @param activityId 活动id
     * @return 是否推送成功
     */
    public boolean sendDDLToGroup(int activityId){

        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if(!optionalActivity.isPresent()){
            throw new NotFoundException("No such activity.");
        }
        Activity activity = optionalActivity.get();
        List<User> targets = new ArrayList<>();
        List<Integer> memberIds = activity.getParticipantIds();
        for(Integer memberId : memberIds){
            targets.add(userRepository.findById(memberId).get());
        }
        for(Integer memberId : activity.getManagerIds()){
            targets.add(userRepository.findById(memberId).get());
        }
        targets.add(userRepository.findById(activity.getCreatorId()).get());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Message message = messageService.createMessage(
                    String.format("活动%s即将开始", activity.getTitle()),
                    formatter.format(activity.getRealBeginDate())
                );


        return messageService.sendMessage(message, targets);

    }

}

package com.codesdream.ase.test;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.Report;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.ActivityService;
import com.codesdream.ase.service.UserService;
import javafx.util.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ActivityServiceTest {
    @Resource
    ActivityService activityService;

    @Resource
    UserService userService;

    //Activity model的增删改查
    @Test
    public void baseFuncTest(){
        User creator = new User();
        creator.setUsername("Tom");
        creator.setPassword("123456");
        creator.getUserAuth().setStudentID("2018303026");
        creator.getUserAuth().setMail("937447984@qq.com");
        creator.getUserAuth().setUserQuestion("Your favourite animal?");
        creator.getUserAuth().setUserAnswer("Cat");
        creator.getUserDetail().setAtSchool(true);
        creator.getUserDetail().setRealName("张三");
        Pair<Boolean, User> checker = userService.checkIfUserExists("Tom");
        if(checker.getKey()){
            userService.delete(checker.getValue());
        }
        creator = userService.save(creator);
        Activity activity = new Activity();
        activity.setTitle("活动1");

        activity.setCreator(creator);
        activity.setType("lo");
        activity.setChiefManager(creator);
        Report report = new Report();
        report.setTitle("活动1的报告");
        activity = activityService.save(activity);
        activity = activityService.addReport(activity, report);
        //activityService.delete();
        //Activity activity1 = new Activity("活动2");
    }

}

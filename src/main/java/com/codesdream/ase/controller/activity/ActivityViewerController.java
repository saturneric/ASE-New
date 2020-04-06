package com.codesdream.ase.controller.activity;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.ActivityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/forget/act")
public class ActivityViewerController {


    @Resource
    ActivityService activityService;

    @GetMapping("/created_acts")
    String showCreated() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityService.findCreatedActs(user);
        return JSONObject.toJSONString(activities);
    }

    @GetMapping("/parting_acts")
    String showParticipate(HttpServletRequest request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityService.findParticipatingActs(user);
        return JSONObject.toJSONString(activities);
    }

    @GetMapping("/parted_acts")
    String showParticipated() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityService.findParticipatedActs(user);
        return JSONObject.toJSONString(activities);
    }

    @GetMapping("/sign_acts")
    String showSign() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityService.findSignActs(user);
        return JSONObject.toJSONString(activities);
    }

    @GetMapping("/vis_acts")
    String showVisible() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityService.findVisibleActs(user);
        return JSONObject.toJSONString(activities);
    }

    @GetMapping("/all")
    String showAll() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityService.findAll(user);
        return JSONObject.toJSONString(activities);
    }

    @GetMapping("/main_responsible_acts")
    String showMainResponsible() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityService.findMainResponsibleActs(user);
        return JSONObject.toJSONString(activities);
    }

    @GetMapping("/sec_responsible_acts")
    String showSecondaryResponsible() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityService.findSecondaryResponsibleActs(user);
        return JSONObject.toJSONString(activities);
    }

    @GetMapping("/acts_by_type")
    String showInType(@RequestParam(defaultValue = "考勤", name = "type") String type) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Activity> activities = activityService.findActivityByType(user, type);
        return JSONObject.toJSONString(activities);
    }
}

package com.codesdream.ase.controller.activity;

import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.repository.activity.ActivityRepository;
import com.codesdream.ase.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class ActivityViewerController {

    private final String url = "/forget/activity";

    @Resource
    ActivityService activityService;

    @Resource
    ActivityRepository activityRepository;

    @RequestMapping(value = url + "/my/participated", method = RequestMethod.GET)
    String showParticipated(Model model, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        ASESpringUtil aseSpringUtil = new ASESpringUtil();
        activityRepository = aseSpringUtil.getBean(ActivityRepository.class);
        //List<Activity> participatedActivities = activityRepository.findc
        return "/my/participated";
    }
}

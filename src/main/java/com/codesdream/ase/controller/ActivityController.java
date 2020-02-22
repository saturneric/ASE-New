package com.codesdream.ase.controller;

import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.configure.ActivityFormConfigure;
import com.codesdream.ase.exception.InvalidFormFormatException;
import com.codesdream.ase.service.ActivityService;
import com.codesdream.ase.validator.WebFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ActivityController {

    @Resource
    ActivityService activityService;

    @RequestMapping(value = "/activity_creator")
    String activityCreatorView(Model model){return "activity_creator";}

    @PostMapping(value = "/activity_creator")
    String activityCreator(Model model, HttpServletRequest request) throws InvalidFormFormatException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        ASESpringUtil aseSpringUtil = new ASESpringUtil();
        ActivityFormConfigure activityFormConfigure = aseSpringUtil.getBean(ActivityFormConfigure.class);
        WebFormValidator webFormValidator = aseSpringUtil.getBean(WebFormValidator.class);
        if(!webFormValidator.check(activityFormConfigure.getStdActivityForm(), parameterMap)){
            throw new InvalidFormFormatException("Invalid activity form.");
        }

        return "act_created";
    }
}



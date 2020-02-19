package com.codesdream.ase.controller;

import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.configure.ActivityFormConfigure;
import com.codesdream.ase.configure.AppConfigure;
import com.codesdream.ase.exception.InvalidFormFormatException;
import com.codesdream.ase.service.ActivityService;
import com.codesdream.ase.validator.WebFormValidator;
import org.springframework.http.HttpRequest;
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

    @RequestMapping(value = "/activityCreator")
    String activityCreatorView(Model model){return "activityCreator";}

    @PostMapping(value = "/activityCreator")
    String activityCreator(Model model, HttpServletRequest request) throws InvalidFormFormatException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        ASESpringUtil aseSpringUtil = new ASESpringUtil();
        ActivityFormConfigure activityFormConfigure = aseSpringUtil.getBean(ActivityFormConfigure.class);
        WebFormValidator webFormValidator = aseSpringUtil.getBean(WebFormValidator.class);
        HashMap<String, Boolean> checkResult = webFormValidator.check(activityFormConfigure.stdActivityMap, parameterMap);
        if(checkResult.get("activity").equals(false)){
            throw new InvalidFormFormatException("Invalid activity form.");
        }
        return "act_created";
    }
}


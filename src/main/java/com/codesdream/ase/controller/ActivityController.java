package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.configure.ActivityFormConfigure;
import com.codesdream.ase.exception.InvalidFormFormatException;
import com.codesdream.ase.exception.LackOfActivityInformation;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.service.ActivityService;
import com.codesdream.ase.validator.NullValueValidator;
import com.codesdream.ase.validator.WebFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ActivityController {

    @Resource
    ActivityService activityService;

    @Resource
    JSONParameter jsonParameter;

    @RequestMapping(value = "/activity_creator")
    String activityCreatorView(Model model){return "activity_creator";}

    @PostMapping(value = "/activity_creator")
    String activityCreator(Model model, HttpServletRequest request) throws InvalidFormFormatException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        ASESpringUtil aseSpringUtil = new ASESpringUtil();
        ActivityFormConfigure activityFormConfigure = aseSpringUtil.getBean(ActivityFormConfigure.class);
        WebFormValidator webFormValidator = aseSpringUtil.getBean(WebFormValidator.class);
        if(!webFormValidator.check(activityFormConfigure.getStdActivityForm(), parameterMap)) {
            throw new InvalidFormFormatException("Invalid activity form.");
        }
        // 需要检查JSON是否合法
        Optional<JSONObject> jsonObject = jsonParameter.getJSONByRequest(request);
        if(!jsonObject.isPresent()) return "error";
        Activity activity = jsonObject.get().toJavaObject(Activity.class);

        NullValueValidator nullValueValidator = aseSpringUtil.getBean(NullValueValidator.class);
        List<String> nullValues = nullValueValidator.checkNullValues(activity);

        return "act_created";
    }
}



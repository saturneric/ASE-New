package com.codesdream.ase.controller.activity;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.component.activity.ActivityConverter;
import com.codesdream.ase.component.activity.NullValueAttributes;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.respond.JSONStandardFailedRespond;
import com.codesdream.ase.configure.ActivityFormConfigure;
import com.codesdream.ase.exception.innerservererror.InvalidFormFormatException;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.UserActivity;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.activity.UserActivityRepository;
import com.codesdream.ase.repository.permission.UserRepository;
import com.codesdream.ase.service.ActivityService;
import com.codesdream.ase.validator.ActivityValidator;
import com.codesdream.ase.validator.NullValueValidator;
import com.codesdream.ase.validator.JSONFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Controller
public class ActivityCreatorController {

    @Resource
    ActivityService activityService;

    @Resource
    JSONParameter jsonParameter;

    @Resource
    ASESpringUtil aseSpringUtil;

    @Resource
    ActivityFormConfigure activityFormConfigure;

    @Resource
    JSONFormValidator jsonFormValidator;

    @Resource
    NullValueValidator nullValueValidator;

    @Resource
    NullValueAttributes nullValueAttributes;

    @Resource
    ActivityValidator activityValidator;

    @Resource
    UserRepository userRepository;

    @Resource
    UserActivityRepository userActivityRepository;

    @Resource
    ActivityConverter activityConverter;

    private final String url = "/forget/activity";

    @RequestMapping(value = url + "/activity_creator")
    String activityCreatorView(Model model){return "activity_creator";}


    @PostMapping(value = url + "/activity_creator")
    @ResponseBody
    String activityCreator(HttpServletRequest request) throws InvalidFormFormatException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        JSONObject error = new JSONObject();
        aseSpringUtil = new ASESpringUtil();
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if (!json.isPresent()) return jsonParameter.getJSONString(new JSONStandardFailedRespond());

        //WebFormValidator webFormValidator = aseSpringUtil.getBean(WebFormValidator.class);
        List<String> formatCheckResult = jsonFormValidator.check(activityFormConfigure.getStdActivityForm(), json.get());
        if (!formatCheckResult.isEmpty()) {
            error.put("error", formatCheckResult);
            return error.toJSONString();
        }
        // 需要检查JSON是否合法

        Activity activity = activityConverter.convertToActivity(json);

        //NullValueValidator nullValueValidator = aseSpringUtil.getBean(NullValueValidator.class);
        List<String> nullValues = nullValueValidator.checkNullValues(activity);
        //= aseSpringUtil.getBean(NullValueAttributes.class);
        for (String str : nullValues){
            if(str.equals("title")){
                nullValueAttributes.getNullValueAttributes().add("title");
            }
            else if(str.equals("creator")){
                nullValueAttributes.getNullValueAttributes().add("creator");
            }
            else if(str.equals("type")){
                nullValueAttributes.getNullValueAttributes().add("type");
            }
            else if(str.equals("planPeriod")){
                nullValueAttributes.getNullValueAttributes().add("planPeriod");
            }
            else if(str.equals("chiefManager")){
                nullValueAttributes.getNullValueAttributes().add("chiefManager");
            }
        }
        //如果为空，存下此活动并跳转至成功创建页面
        if(nullValueAttributes.getNullValueAttributes().isEmpty()){

            //ActivityValidator activityValidator = aseSpringUtil.getBean(ActivityValidator.class);
            String[] errorParameters = activityValidator.check(json);
            if(errorParameters != null){
                JSONObject invalidParameters = new JSONObject();
                invalidParameters.put("invalid_parameters", errorParameters);
                return invalidParameters.toJSONString();

            }
            else{
                //UserRepository userRepository = aseSpringUtil.getBean(UserRepository.class);
                //activityService = aseSpringUtil.getBean(ActivityService.class);
                activity = activityService.createActivity(activity);
                String username = json.get().get("creator").toString();
                Optional<User> user = userRepository.findByUsername(username);
                //UserActivityRepository userActivityRepository = aseSpringUtil.getBean(UserActivityRepository.class);
                UserActivity userActivity = userActivityRepository.findByUser(user.get());
                userActivity.getCreatedActivities().add(activity);
                userActivityRepository.save(userActivity);
            }
        }
        //否则返回一个JSON对象给前端
        else{
            JSONObject nullParameters = new JSONObject();
            nullParameters.put("null_values",nullValueAttributes.getNullValueAttributes());
            return nullParameters.toJSONString();
        }
        return url + "/act_created";
    }
}



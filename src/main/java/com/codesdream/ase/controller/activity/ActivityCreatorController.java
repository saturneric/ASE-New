package com.codesdream.ase.controller.activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.component.activity.NullValueAttributes;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.respond.JSONBaseRespondObject;
import com.codesdream.ase.configure.ActivityFormConfigure;
import com.codesdream.ase.exception.InvalidFormFormatException;
import com.codesdream.ase.exception.UserNotFoundException;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.UserActivity;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.activity.UserActivityRepository;
import com.codesdream.ase.repository.permission.UserRepository;
import com.codesdream.ase.service.ActivityService;
import com.codesdream.ase.validator.ActivityValidator;
import com.codesdream.ase.validator.NullValueValidator;
import com.codesdream.ase.validator.WebFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
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
    WebFormValidator webFormValidator;

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

    private final String url = "/forget/activity";

    @RequestMapping(value = url + "/activity_creator")
    String activityCreatorView(Model model){return "activity_creator";}


    @PostMapping(value = url + "/activity_creator")
    @ResponseBody
    String activityCreator(@RequestBody JSONObject jsonParam, Model model, HttpServletRequest request) throws InvalidFormFormatException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        JSONObject error = new JSONObject();

        Map<String, String[]> parameterMap = request.getParameterMap();
        aseSpringUtil = new ASESpringUtil();
        //WebFormValidator webFormValidator = aseSpringUtil.getBean(WebFormValidator.class);
        if(!webFormValidator.check(activityFormConfigure.getStdActivityForm(), parameterMap)) {
            error.put("error", "Invalid form format");
            return error.toJSONString();
        }
        // 需要检查JSON是否合法
        Optional<JSONObject> jsonObject = jsonParameter.getJSONByRequest(request);
        if(!jsonObject.isPresent()){
            error.put("error", "Invalid type.");
            return error.toJSONString();
        }
        Activity activity = jsonObject.get().toJavaObject(Activity.class);

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
            String[] errorParameters = activityValidator.check(parameterMap);
            if(errorParameters != null){
                JSONObject invalidParameters = new JSONObject();
                invalidParameters.put("invalid_parameters", errorParameters);
                return invalidParameters.toJSONString();

            }
            else{
                //UserRepository userRepository = aseSpringUtil.getBean(UserRepository.class);
                //activityService = aseSpringUtil.getBean(ActivityService.class);
                activity = activityService.createActivity(activity);
                String username = parameterMap.get("creator")[0];
                Optional<User> user = userRepository.findByUsername(username);
                //UserActivityRepository userActivityRepository = aseSpringUtil.getBean(UserActivityRepository.class);
                UserActivity userActivity = userActivityRepository.findByUser(user.get());
                userActivity.getCreatedActivities().add(activity);
                userActivityRepository.save(userActivity);
            }
        }
        //否则返回一个JSON对象给前端，此处应该是JSP页面，动态标红
        else{
            JSONObject nullParameters = new JSONObject();
            nullParameters.put("null_values",nullValueAttributes.getNullValueAttributes());
            return nullParameters.toJSONString();
        }
        return url + "/act_created";
    }
}



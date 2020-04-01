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
import com.codesdream.ase.service.ActivityService;
import com.codesdream.ase.service.UserService;
import com.codesdream.ase.validator.ActivityValidator;
import com.codesdream.ase.validator.NullValueValidator;
import com.codesdream.ase.validator.JSONFormValidator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
    UserService userService;

    @Resource
    UserActivityRepository userActivityRepository;

    @Resource
    ActivityConverter activityConverter;

    private final String url = "/forget/activity";


    @PostMapping(value = url + "/activity_creator")
    @ResponseBody
    @ApiOperation(value = "创建活动", notes = "所有有关用户的数据传递均使用id，类型为int")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "活动标题", required = true),
            @ApiImplicitParam(name = "type", value = "活动类型", required = true),
            @ApiImplicitParam(name = "start-time", value = "活动开始时间，格式为yyyy-MM-dd HH:mm:ss", required = true),
            @ApiImplicitParam(name = "end-time", value = "活动结束时间，格式为yyyy-MM-dd HH:mm:ss", required = true),
            @ApiImplicitParam(name = "chief-manager", value = "主要负责人", required = true),
            @ApiImplicitParam(name = "assist-managers", value = "次要负责人"),
            @ApiImplicitParam(name = "description", value = "活动描述"),
            @ApiImplicitParam(name = "cycle", value = "活动周期，格式为阿拉伯数字数字+单位，0表示无周期"),
            @ApiImplicitParam(name = "participate-group", value = "预定参与人员"),
            @ApiImplicitParam(name = "sign-group", value = "可参与人员"),
            @ApiImplicitParam(name = "inform-group", value = "通知人群，若为空，则默认为预定参与人员和可报名人员的并集"),
            @ApiImplicitParam(name = "visible-group", value = "活动可见人群，若为空，则默认为负责人、活动创建者预定参和可报名人员以及通知人员的并集"),
            @ApiImplicitParam(name = "remind-time", defaultValue = "30m", value = "活动提醒时间，格式为数字+单位，可接受的单位从大到小有:w,d,h,m,s"),
    })
    String activityCreator(HttpServletRequest request) throws InvalidFormFormatException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        JSONObject error = new JSONObject();
        aseSpringUtil = new ASESpringUtil();
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if (!json.isPresent()) return jsonParameter.getJSONString(new JSONStandardFailedRespond());

        List<String> formatCheckResult = jsonFormValidator.check(activityFormConfigure.getStdActivityForm(), json.get());

        if (!formatCheckResult.isEmpty()) {
            error.put("error", formatCheckResult);
            return error.toJSONString();
        }
        // 需要检查JSON是否合法
        Activity activity = activityConverter.convertToActivity(json);
        List<String> nullValues = nullValueValidator.checkNullValues(activity);

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
        if (!nullValueAttributes.getNullValueAttributes().isEmpty()) {

            String[] errorParameters = activityValidator.check(json);
            if(errorParameters != null){
                JSONObject invalidParameters = new JSONObject();
                invalidParameters.put("invalid_parameters", errorParameters);
                return invalidParameters.toJSONString();

            }
            else{
                activity = activityService.createActivity(activity);
                String username = json.get().get("creator").toString();
                Optional<User> user = userService.findUserByUsername(username);
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



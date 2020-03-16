package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.respond.JSONStandardFailedRespond;
import com.codesdream.ase.component.json.respond.JSONBaseRespondObject;
import com.codesdream.ase.component.permission.ASEUsernameEncoder;
import com.codesdream.ase.component.json.request.UserLoginChecker;
import com.codesdream.ase.component.json.respond.UserLoginCheckerJSONRespond;
import com.codesdream.ase.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


/**
 * 登录界面控制器类
 */
@Slf4j
@Controller
public class LoginController {

    @Resource
    private JSONParameter jsonParameter;

    @Resource
    private IUserService userService;

    @Resource
    private ASEUsernameEncoder usernameEncoder;

    @RequestMapping(value = "/login")
    String printLogin(Model model) {
        return "login";
    }

    @RequestMapping(value = "/login/check_exists", method = RequestMethod.POST)
    @ResponseBody
    String checkExists(HttpServletRequest request){

        // 检查是否为JSON
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if(!json.isPresent()) return jsonParameter.getJSONString(new JSONStandardFailedRespond());


        UserLoginChecker loginChecker = json.get().toJavaObject(UserLoginChecker.class);

        // 检查学号对应的用户名是否存在
        if(loginChecker.getCheckType().equals("UsernameExistChecker")){
            // 根据学号计算用户名
            String user = userService.getUsernameByStudentId(loginChecker.getUsername());
            // 查询用户名存在状态
            boolean existStatus = userService.checkIfUserExists(user).getKey();
            // 构造返回对象
            UserLoginCheckerJSONRespond respond = new UserLoginCheckerJSONRespond();
            respond.setUserExist(existStatus);
            return jsonParameter.getJSONString(respond);
        }
        else {
            // 返回失败对象
            return jsonParameter.getJSONString(new JSONStandardFailedRespond());
        }
    }

    // 根据学号计算对应的username
    @RequestMapping(value = "/login/check_uid", method = RequestMethod.POST)
    @ResponseBody
    String checkUsernameByStudentID(HttpServletRequest request){
        // 检查是否为JSON
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if(!json.isPresent()) return jsonParameter.getJSONString(new JSONStandardFailedRespond());

        UserLoginChecker loginChecker = json.get().toJavaObject(UserLoginChecker.class);

        if(loginChecker.getCheckType().equals("UIDGeneratorChecker")) {
            UserLoginCheckerJSONRespond respond = new UserLoginCheckerJSONRespond();
            respond.setRespondInformation(userService.getUsernameByStudentId(loginChecker.getUsername()));
            return jsonParameter.getJSONString(respond);
        }
        else {
            // 返回失败对象
            return jsonParameter.getJSONString(new JSONStandardFailedRespond());
        }


    }



}

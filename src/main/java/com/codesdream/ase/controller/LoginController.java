package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.datamanager.RespondJSONBaseObject;
import com.codesdream.ase.component.permission.ASEUsernameEncoder;
import com.codesdream.ase.component.permission.UserLoginChecker;
import com.codesdream.ase.component.permission.UserLoginCheckerRespond;
import com.codesdream.ase.service.IUserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


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

    @RequestMapping(value = "/login/check", method = RequestMethod.POST)
    @ResponseBody
    String checkLogin(HttpServletRequest request){
        log.info("Get Login Check Request");
        JSONObject json = jsonParameter.getJSONByRequest(request);
        UserLoginChecker loginChecker = json.toJavaObject(UserLoginChecker.class);
        // 检查类型
        if(loginChecker.getCheckType().equals("UsernameExistChecker")){
            // 计算用户名
            String user = usernameEncoder.encode(loginChecker.getUsername()) ;
            // 查询用户名存在状态
            boolean existStatus = userService.checkIfUserExists(user).getKey();
            // 构造返回对象
            UserLoginCheckerRespond respond = new UserLoginCheckerRespond();
            respond.setUserExist(existStatus);
            return jsonParameter.getJSONString(respond);

        }
        else {
            // 返回失败对象
            return jsonParameter.getJSONString(new RespondJSONBaseObject());
        }
    }

}

package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.api.QuickJSONRespond;
import com.codesdream.ase.component.json.respond.JSONStandardFailedRespond;
import com.codesdream.ase.component.json.request.UserLoginChecker;
import com.codesdream.ase.component.json.respond.UserLoginCheckerJSONRespond;
import com.codesdream.ase.service.IAuthService;
import com.codesdream.ase.service.IUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api(tags = "用户登录有关接口")
public class LoginController {

    @Resource
    private JSONParameter jsonParameter;

    @Resource
    private QuickJSONRespond quickJSONRespond;

    @Resource
    private IUserService userService;

    @Resource
    private IAuthService authService;

    @PostMapping(value = "/login/check_exists")
    @ResponseBody
    String checkExists(HttpServletRequest request){

        // 检查是否为JSON
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if(!json.isPresent()) return quickJSONRespond.getRespond400("Invalid JSON Form");


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
            return quickJSONRespond.getRespond200(null, respond);
        }
        else {
            // 返回失败对象
            return quickJSONRespond.getRespond400("CheckType Mismatch");
        }
    }

    // 根据学号计算对应的username
    @RequestMapping(value = "/login/check_uid", method = RequestMethod.POST)
    @ResponseBody
    String checkUsernameByStudentID(HttpServletRequest request){

        String preValidationCode = request.getHeader("pvc");

        // 检查随机预验证码
        if(preValidationCode == null || !authService.preValidationCodeChecker(preValidationCode))
            return quickJSONRespond.getRespond403("Invalid PreValidationCode");

        // 检查是否为JSON
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if(!json.isPresent()) return jsonParameter.getJSONString(new JSONStandardFailedRespond());

        UserLoginChecker loginChecker = json.get().toJavaObject(UserLoginChecker.class);

        if(loginChecker.getUsername() == null || loginChecker.getCheckType() == null)
            return quickJSONRespond.getRespond406("Request Violates The Interface Protocol");

        if(loginChecker.getCheckType().equals("UIDGeneratorChecker")) {
            UserLoginCheckerJSONRespond respond = new UserLoginCheckerJSONRespond();
            respond.setUid(userService.getUsernameByStudentId(loginChecker.getUsername()));
            return quickJSONRespond.getRespond200(null, respond);
        }
        else {
            // 返回失败对象
            return quickJSONRespond.getRespond400("CheckType Mismatch");
        }
    }



}

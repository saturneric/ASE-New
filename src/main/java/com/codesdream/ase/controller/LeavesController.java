package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.respond.FailedSONRespond;
import com.codesdream.ase.component.json.respond.JSONBaseRespondObject;
import com.codesdream.ase.component.permission.ASEUsernameEncoder;
import com.codesdream.ase.component.json.request.UserLeaveChecker;
import com.codesdream.ase.component.json.respond.UserLeaveCheckerJSONRespond;
import com.codesdream.ase.service.LeavesService;
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
public class LeavesController {

    @Resource
    private JSONParameter jsonParameter;

    @Resource
    private LeavesService leavesService;

    @Resource
    private ASEUsernameEncoder usernameEncoder;

    @RequestMapping(value = "/")
    String printLeave(Model model) {
        return "Leave";
    }

    @RequestMapping(value = "/Leave/check", method = RequestMethod.POST)
    @ResponseBody
    String checkLeave(HttpServletRequest request){

        // 检查是否为JSON
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
        if(!json.isPresent()) return jsonParameter.getJSONString(new FailedSONRespond());


        UserLeaveChecker LeaveChecker = json.get().toJavaObject(UserLeaveChecker.class);
        // 检查类型
        if(LeaveChecker.getCheckType().equals("UsernameExistChecker")){
            // 根据学号计算用户名
            String user = usernameEncoder.encode(LeaveChecker.getUsername()) ;
            // 查询用户名存在状态
            boolean existStatus = userService.checkIfUserExists(user).getKey();
            // 构造返回对象
            UserLeaveCheckerJSONRespond respond = new UserLeaveCheckerJSONRespond();
            respond.setUserExist(existStatus);
            return jsonParameter.getJSONString(respond);
        }
        else {
            // 返回失败对象
            return jsonParameter.getJSONString(new JSONBaseRespondObject());
        }
    }

}

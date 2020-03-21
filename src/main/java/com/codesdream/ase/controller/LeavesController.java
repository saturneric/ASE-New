package com.codesdream.ase.controller;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.request.UserLeaveRequest;
//import com.codesdream.ase.component.json.respond.FailedSONRespond;
import com.codesdream.ase.component.json.respond.JSONBaseRespondObject;
import com.codesdream.ase.component.permission.ASEUsernameEncoder;
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

    //提交请假申请
    @RequestMapping(value = "/Leave/check", method = RequestMethod.POST)
    @ResponseBody
    String requestLeave(HttpServletRequest request){

        // 检查是否为JSON
        Optional<JSONObject> json = jsonParameter.getJSONByRequest(request);
       // if(!json.isPresent()) return jsonParameter.getJSONString(new FailedSONRespond());


        UserLeaveRequest LeaveChecker = json.get().toJavaObject(UserLeaveRequest.class);
        // 检查类型
          return jsonParameter.getJSONString(request);

    }
    //列出某辅导员待审核名单

    //列出某人

}

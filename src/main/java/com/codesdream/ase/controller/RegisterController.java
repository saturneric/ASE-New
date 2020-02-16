package com.codesdream.ase.controller;

import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class RegisterController {
    @Resource
    UserService userService;

    @RequestMapping(value = "/register")
    String registerView(Model model){
        return "register";
    }

    // 处理注册表单
    @PostMapping(value = "/register")
    String doRegister(Model model, HttpServletRequest request){
        Map<String, String[]> parameterMap= request.getParameterMap();

        // 进行处理前的检查
        if(parameterMap.containsKey("student-id")
                && parameterMap.containsKey("password")
                && parameterMap.containsKey("retry-password")
                && parameterMap.containsKey("user-question")
                && parameterMap.containsKey("user-answer")
        ) {
            User user = userService.getDefaultUser();
            String student_id = parameterMap.get("student-id")[0].toString();
            // 生成随机用户名
            userService.generateRandomUsernameByStudentID(user, student_id);

            String password = parameterMap.get("password")[0].toString();
            String retry_password =  parameterMap.get("retry-password")[0].toString();

            if (password.equals(retry_password)) {
                user.setPassword(password);
                userService.save(user);
                // 返回登录界面
                return "login";
            }

        }

        return "register";
    }

}

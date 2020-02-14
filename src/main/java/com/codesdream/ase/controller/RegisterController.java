package com.codesdream.ase.controller;

import com.codesdream.ase.component.ASEPasswordEncoder;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.UserRepository;
import com.codesdream.ase.service.UserService;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

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
        if(parameterMap.containsKey("username")
                && parameterMap.containsKey("password")
                && parameterMap.containsKey("retry-password")
                && parameterMap.containsKey("user-question")
                && parameterMap.containsKey("user-answer")
        ) {
            User user = userService.getDefaultUser();
            user.setUsername(parameterMap.get("username")[0].toString());

            String password = parameterMap.get("password")[0].toString();
            String retry_password =  parameterMap.get("retry-password")[0].toString();

            if (password.equals(retry_password)) {
                user.setPassword(password);
                userService.save(user);

                return "index";
            }

        }

        return "register";
    }

}

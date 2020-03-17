package com.codesdream.ase.controller;

import com.codesdream.ase.model.information.BaseStudentInfo;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.BaseInformationService;
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
    private UserService userService;

    @Resource
    private BaseInformationService baseInformationService;

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
            // 获得提交学号
            String student_id = parameterMap.get("student-id")[0].toString();
            // 获得密保问题
            String user_question = parameterMap.get("user-question")[0].toString();
            // 获得密保答案
            String user_answer = parameterMap.get("user-answer")[0].toString();

            // 检查用户的基本信息是否录入系统
            if(!baseInformationService.checkStudentInfo(student_id))
                throw new RuntimeException("Student ID Not Found In Base Information Service");

            // 查找对应的基本信息
            BaseStudentInfo studentInfo = baseInformationService.findStudentInfoByStudentId(student_id);

            // 根据基本信息生成对应用户
            User user = userService.getUserByStudentInfo(studentInfo);

            // 填充密保问题
            user.getUserAuth().setUserQuestion(user_question);
            user.getUserAuth().setUserAnswer(user_answer);
            user.getUserAuth().setMail("");

            String password = parameterMap.get("password")[0].toString();
            String retry_password =  parameterMap.get("retry-password")[0].toString();

            if (password.equals(retry_password)) {
                user.setPassword(password);
                userService.save(user);
                // 返回登录界面
                return "login";
            }
            else{
                throw  new RuntimeException("Retry Password Not Correct");
            }

        }

        return "register";
    }

}

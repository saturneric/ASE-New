package com.codesdream.ase.controller;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.datamanager.QuickJSONRespond;
import com.codesdream.ase.component.json.request.UserRegisterChecker;
import com.codesdream.ase.model.information.BaseStudentInfo;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.BaseInformationService;
import com.codesdream.ase.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class RegisterController {
    @Resource
    private UserService userService;

    @Resource
    private BaseInformationService baseInformationService;

    @Resource
    private JSONParameter jsonParameter;

    @Resource
    private QuickJSONRespond quickJSONRespond;

    @RequestMapping(value = "/register")
    String registerView(Model model){
        return "register";
    }

    // 处理注册表单
    @PostMapping(value = "/register/do_register")
    @ResponseBody
    String doRegister(HttpServletRequest request){

        Optional<UserRegisterChecker> registerCheckerOptional =
                jsonParameter.getJavaObjectByRequest(request, UserRegisterChecker.class);

        // 检查JSON是否完整
        if(!registerCheckerOptional.isPresent()){
            return quickJSONRespond.getRespond400("Illegal JSON Format");
        }

        // 检查数据是否完整
        UserRegisterChecker registerChecker = registerCheckerOptional.get();
        if(registerChecker.getPassword() == null
                || registerChecker.getStudentId() == null
                || registerChecker.getUserAnswer() == null
                || registerChecker.getUserQuestion() == null){
            return  quickJSONRespond.getRespond400("Incomplete Data");
        }

        // 获得提交学号
        String student_id = registerChecker.getStudentId();
        // 获得密保问题
        String user_question = registerChecker.getUserQuestion();
        // 获得密保答案
        String user_answer = registerChecker.getUserAnswer();

        // 检查用户的基本信息是否录入系统
        if(!baseInformationService.checkStudentInfo(student_id))
            return quickJSONRespond.getRespond500("StudentID Base Information Not Found");

        // 检查学号是否已被注册
        if(userService.checkIfUserExists(userService.getUsernameByStudentId(student_id)).getKey()){
            return quickJSONRespond.getRespond500("StudentID Already Used");
        }

        // 查找对应的基本信息
        BaseStudentInfo studentInfo = baseInformationService.findStudentInfoByStudentId(student_id);

        // 根据基本信息生成对应用户
        User user = userService.getUserByStudentInfo(studentInfo);

        // 填充密保问题
        user.getUserAuth().setUserQuestion(user_question);
        user.getUserAuth().setUserAnswer(user_answer);
        user.getUserAuth().setMail("");

        String password = registerChecker.getPassword();

        user.setPassword(password);
        userService.save(user);

        // 成功注册
        return quickJSONRespond.getRespond200("Register Success");
    }

}

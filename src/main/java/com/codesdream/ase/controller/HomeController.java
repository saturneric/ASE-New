package com.codesdream.ase.controller;

import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Optional;

@Controller
public class HomeController {
    @Resource
    IUserService userService;

    @RequestMapping(value = "/home")
    public String  showHomeView(Model model, Principal principal){
        User user = userService.findUserByUsername(principal.getName());
        // 为视图模板指定参数
        model.addAttribute("username", user.getUsername().substring(0, 18));
        model.addAttribute("student_id", user.getUserAuth().getStudentID());
        model.addAttribute("is_at_school", user.getUserDetail().isAtSchool());
        return "home";
    }
}

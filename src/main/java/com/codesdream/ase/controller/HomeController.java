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
        Optional<User> userOptional = userService.findUserByUsername(principal.getName());
        if(!userOptional.isPresent()) return "error";
        User user = userOptional.get();
        // 为视图模板指定参数
        model.addAttribute("username", user.getUsername().substring(0, 18));
        model.addAttribute("real_name", user.getUserDetail().getRealName());
        model.addAttribute("sex", user.getUserDetail().getSex());
        model.addAttribute("student_id", user.getUserAuth().getStudentID());
        model.addAttribute("class_id", user.getUserDetail().getClassId());
        model.addAttribute("college", user.getUserDetail().getBaseCollege().getName());
        model.addAttribute("ethnic", user.getUserDetail().getBaseEthnic().getName());
        model.addAttribute("major", user.getUserDetail().getBaseMajor().getName());
        model.addAttribute("is_at_school", user.getUserDetail().isAtSchool());
        model.addAttribute("ethnic", user.getUserDetail().getBaseEthnic().getName());
        model.addAttribute("mail", user.getUserAuth().getMail());
        return "home";
    }
}

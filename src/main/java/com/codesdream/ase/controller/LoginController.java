package com.codesdream.ase.controller;

import com.codesdream.ase.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @RequestMapping(value = "/login")
    String printLogin(Model model){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String checkLogin(Model model, HttpServletRequest request){
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(DigestUtils.md5DigestAsHex(request.getParameter("password").getBytes()));
        return "login";
    }

}

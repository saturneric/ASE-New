package com.codesdream.ase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 登录界面控制器类
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/login")
    String printLogin(Model model) {
        return "login";
    }
}

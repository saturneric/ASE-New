package com.codesdream.ase.controller;


import com.codesdream.ase.configure.GlobalConfigure;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * 管理界面主页控制器类
 * 现阶段主要用于管理界面主页
 */
@Controller
public class ASEController {
    @Resource
    GlobalConfigure globalConfigure;

    @RequestMapping(value = "/")
    public String printIndex(Model model, Principal principal){
        model.addAttribute("username",principal.getName());
        return "index";
    }
}

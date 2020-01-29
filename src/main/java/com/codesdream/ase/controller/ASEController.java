package com.codesdream.ase.controller;


import com.codesdream.ase.configure.GlobalConfigure;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.Principal;

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

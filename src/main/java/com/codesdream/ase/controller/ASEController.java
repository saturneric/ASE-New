package com.codesdream.ase.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ASEController {

    @RequestMapping(value = "/")
    public String printIndex(){
        return "index";
    }
}

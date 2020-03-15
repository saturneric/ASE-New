package com.codesdream.ase.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

    @RequestMapping("hello")
    String hello(){
        return "hello";
    }
}

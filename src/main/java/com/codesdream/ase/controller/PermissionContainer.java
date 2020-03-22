package com.codesdream.ase.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("pmt")
public class PermissionContainer {

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping("tag")
    public String createTag(HttpServletRequest request, Authentication authentication){
        return "";
    }


}

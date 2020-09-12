package com.codesdream.ase.controller;

import com.codesdream.ase.model.message.Notification;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

    @Resource
    MessageService messageService;

    @PostMapping("notification/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Notification createNotification (@RequestBody String title, @RequestBody String context,
                                            @RequestBody List<Integer> files, Authentication authentication){
        User user = (User)authentication.getPrincipal();
        return null;//messageService.createNotification(title,context,);
    }

}

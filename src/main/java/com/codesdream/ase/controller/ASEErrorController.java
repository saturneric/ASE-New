package com.codesdream.ase.controller;

import com.codesdream.ase.component.error.ErrorResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ASEErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        // 页面未找到
        if(statusCode == HttpStatus.NOT_FOUND.value()){
            return "not_found";
        }
        model.addAttribute("http_status", statusCode);
        model.addAttribute("exception_name", exception.getClass().getName());
        model.addAttribute("exception_message", exception.getMessage());
        List<String> stack_infos = new ArrayList<>();
        for(StackTraceElement element : exception.getStackTrace()){
            String s = element.toString();
            stack_infos.add(s);
        }
        model.addAttribute("error_stack", stack_infos);
        model.addAttribute("exception_date",new Date());
        return "error";

    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

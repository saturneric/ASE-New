package com.codesdream.ase.controller;

import com.codesdream.ase.component.datamanager.QuickJSONRespond;
import com.codesdream.ase.component.error.ErrorResponse;
import com.codesdream.ase.component.json.respond.ErrorInfoJSONRespond;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ASEErrorController implements ErrorController {

/*    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        // 无效异常访问检查
        if(statusCode == null || exception == null)
            model.addAttribute("if_active", false);
        else {

            model.addAttribute("if_active", true);
            // 检查返回的状态
            if (statusCode == HttpStatus.NOT_FOUND.value()) return "not_found";
            model.addAttribute("http_status", statusCode);
            // 检查是否含有引发异常
            if (exception.getCause() == null) {
                model.addAttribute("exception_name", exception.getClass().getName());
                model.addAttribute("exception_message", exception.getMessage());
            } else {
                model.addAttribute("exception_name", exception.getCause().getClass().getName());
                model.addAttribute("exception_message", exception.getCause().getMessage());
            }
            List<String> stack_infos = new ArrayList<>();
            for (StackTraceElement element : exception.getStackTrace()) {
                String s = element.toString();
                stack_infos.add(s);
            }
            model.addAttribute("error_stack", stack_infos);
            model.addAttribute("exception_date", new Date());
        }
        return "error";
    }*/

    @Resource
    private QuickJSONRespond quickJSONRespond;

    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        // 检查返回的状态
        if (statusCode == HttpStatus.NOT_FOUND.value()) return quickJSONRespond.getRespond404(null);
        ErrorInfoJSONRespond errorInfoJSONRespond  = new ErrorInfoJSONRespond();

        // 检查是否含有引发异常
        if (exception.getCause() == null) {
            errorInfoJSONRespond.setException(exception.getClass().getName());
            errorInfoJSONRespond.setExceptionMessage(exception.getMessage());
        } else {
            errorInfoJSONRespond.setException(exception.getCause().getClass().getName());
            errorInfoJSONRespond.setExceptionMessage(exception.getCause().getMessage());
        }
        errorInfoJSONRespond.setDate(new Date());

        return quickJSONRespond.getJSONStandardRespond(
                statusCode,
                "Error Controller Handle",
                null,
                errorInfoJSONRespond);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}

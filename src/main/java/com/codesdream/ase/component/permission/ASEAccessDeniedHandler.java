package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.respond.UserLoginCheckerJSONRespond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 认证用户访问无权限资源
@Slf4j
@Component
public class ASEAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private JSONParameter jsonParameter;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.info("ASEAccessDeniedHandler Found!");

        // 对无权限操作返回403
        response.getWriter().print(jsonParameter.getJSONStandardRespond403());


    }
}

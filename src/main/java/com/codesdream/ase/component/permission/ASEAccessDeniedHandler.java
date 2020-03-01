package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
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

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        UserLoginCheckerRespond checkerRespond = new UserLoginCheckerRespond();
        checkerRespond.setLoginStatus(true);
        checkerRespond.setUserExist(true);
        checkerRespond.setRespondInformation("Authenticated user has no access to this resource");

        // 对匿名用户返回
        response.getWriter().print(jsonParameter.getJSONString(checkerRespond));

    }
}

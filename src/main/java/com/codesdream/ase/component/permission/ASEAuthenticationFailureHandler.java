package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.datamanager.QuickJSONRespond;
import com.codesdream.ase.component.json.respond.UserLoginCheckerJSONRespond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 认证失败返回
@Slf4j
@Component
public class ASEAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Resource
    private QuickJSONRespond quickJSONRespond;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException
    {
        log.info("ASEAuthenticationFailureHandler Login Fail!");

        // 认证失败返回406
        response.getWriter().write(quickJSONRespond.getRespond406("Authentication Failure"));
    }
}

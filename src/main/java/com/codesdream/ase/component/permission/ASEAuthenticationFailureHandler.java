package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
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
    private JSONParameter jsonParameter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException
    {
        log.info("ASEAuthenticationFailureHandler Login Fail!");
        UserLoginCheckerJSONRespond respond = new UserLoginCheckerJSONRespond();

        respond.setUserExist(null);
        respond.setUserBanned(null);
        respond.setLoginStatus(false);
        respond.setRespondInformation("Authentication Failed");

        // 填充response对象
        response.getWriter().write(jsonParameter.getJSONStandardRespond200(respond));
    }
}

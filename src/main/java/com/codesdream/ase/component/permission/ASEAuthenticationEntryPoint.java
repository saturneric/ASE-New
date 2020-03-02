package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.respond.UserLoginCheckerJSONRespond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 匿名用户访问无权限资源
@Slf4j
@Component
public class ASEAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Resource
    private JSONParameter jsonParameter;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.info("ASEAuthenticationEntryPoint Found!");

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        UserLoginCheckerJSONRespond checkerRespond = new UserLoginCheckerJSONRespond();
        checkerRespond.setLoginStatus(false);
        checkerRespond.setUserExist(false);
        checkerRespond.setUserBanned(true);
        checkerRespond.setRespondInformation("Anonymous user has no access to this resource");

        // 对匿名用户返回
        response.getWriter().print(jsonParameter.getJSONString(checkerRespond));

    }
}

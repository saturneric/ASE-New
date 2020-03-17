package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.datamanager.QuickJSONRespond;
import com.codesdream.ase.component.json.respond.JSONBaseRespondObject;
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
    private QuickJSONRespond quickJSONRespond;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        // 对匿名用户返回401
        response.getWriter().print(quickJSONRespond.getRespond401(null));

    }
}

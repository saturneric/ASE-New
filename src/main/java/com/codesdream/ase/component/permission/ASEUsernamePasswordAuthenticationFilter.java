package com.codesdream.ase.component.permission;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 登录验证过滤器
public class ASEUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    // 常量
    public static final String SPRING_SECURITY_RESTFUL_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_RESTFUL_PASSWORD_KEY = "password";
    public static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/login";

    private String usernameParameter = SPRING_SECURITY_RESTFUL_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_RESTFUL_PASSWORD_KEY;
    private boolean postOnly = true;

    @Resource
    private JSONParameter jsonParameter;

    protected ASEUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher(SPRING_SECURITY_RESTFUL_LOGIN_URL, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        // 检查提交方式
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication Method NOT Supported: " + request.getMethod());
        }

        UserLoginChecker checker = jsonParameter.getJavaObjectByRequest(request, UserLoginChecker.class);
        if(!checker.getCheckType().equals("UserLoginChecker"))
            throw new AuthenticationServiceException("Invalid Checker Type");

        String username = checker.getUsername();
        String password = checker.getPassword();

        if(username == null) username = "";
        if(password == null) password = "";

        // 去除首尾两端的空白字符
        username = username.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

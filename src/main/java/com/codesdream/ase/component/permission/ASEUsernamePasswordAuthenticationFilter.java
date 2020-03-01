package com.codesdream.ase.component.permission;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.datamanager.JSONParameter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// 登录验证过滤器
@Slf4j
public class ASEUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Resource
    private JSONParameter jsonParameter;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 判断是否为JSON格式的数据
        log.info(String.format("Content Type: %s", request.getContentType()));
        if(request.getContentType().equals("application/x-www-form-urlencoded; charset=UTF-8")) {

            UserLoginChecker checker = jsonParameter.getJavaObjectByRequest(request, UserLoginChecker.class);
            if (!checker.getCheckType().equals("From"))
                throw new AuthenticationServiceException("Invalid Checker Type");

            String username = checker.getUsername();
            String password = checker.getPassword();

            if (username == null) username = "";
            if (password == null) password = "";

            // 去除首尾两端的空白字符
            username = username.trim();

            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(username, password);

            log.info(String.format("User Authentication: %s %s.", username, password));

            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }
        else{
                return super.attemptAuthentication(request, response);
        }
    }
}

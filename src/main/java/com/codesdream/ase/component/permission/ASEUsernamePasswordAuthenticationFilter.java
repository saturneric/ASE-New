package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.request.UserLoginChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

// 登录验证过滤器
@Slf4j
public class ASEUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Resource
    private JSONParameter jsonParameter;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // 判断是否为AJAX请求格式的数据
        if(Optional.ofNullable(request.getHeader("X-Requested-With")).isPresent()) {

            Optional<UserLoginChecker> checker = jsonParameter.getJavaObjectByRequest(request, UserLoginChecker.class);
            if(!checker.isPresent()) throw new BadCredentialsException("Invalid AJAX JSON Request");
            if (!checker.get().getCheckType().equals("From"))
                throw new AuthenticationServiceException("Invalid Checker Type");

            // 获得相应的用户名密码
            String username = checker.get().getUsername();
            String password = checker.get().getPassword();

            if (username == null) username = "";
            if (password == null) password = "";

            // 去除首尾两端的空白字符
            username = username.trim();
            password = password.trim();

            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(username, password);


            log.info(String.format("User AJAX JSON Authentication: %s %s.", username, password));

            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        }
        else{
                return super.attemptAuthentication(request, response);
        }
    }
}

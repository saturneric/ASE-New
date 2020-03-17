package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.auth.AJAXRequestChecker;
import com.codesdream.ase.component.auth.JSONTokenUsernamePasswordAuthenticationToken;
import com.codesdream.ase.component.auth.TimestampExpiredChecker;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.request.UserLoginChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

// 登录验证过滤器
@Slf4j
public class ASEUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Resource
    private JSONParameter jsonParameter;

    @Resource
    private AJAXRequestChecker ajaxRequestChecker;

    @Resource
    private TimestampExpiredChecker timestampExpiredChecker;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String timestamp =  request.getHeader("timestamp");

        // 检查时间戳是否合理(60秒内)
        if(timestamp == null || !timestampExpiredChecker.checkTimestampBeforeMaxTime(timestamp, 60)){
            throw new AuthenticationServiceException("Timestamp Expired.");
        }

        // 判断是否为AJAX请求格式的数据
        if(!ajaxRequestChecker.checkAjaxPOSTRequest(request)) {
            throw new AuthenticationServiceException("Authentication method not supported: NOT Ajax Method.");
        }

        Optional<UserLoginChecker> checker = jsonParameter.getJavaObjectByRequest(request, UserLoginChecker.class);
        if(!checker.isPresent()) throw new BadCredentialsException("Invalid AJAX JSON Request");

        if (!checker.get().getCheckType().equals("UsernamePasswordChecker"))
            throw new AuthenticationServiceException("Authentication not supported: NOT Username Password Type.");

        // 获得相应的用户名密码
        String username = checker.get().getUsername();
        String password = checker.get().getPassword();
        String clientCode = checker.get().getClientCode();

        if (username == null) username = "";
        if (password == null) password = "";

        // 去除首尾两端的空白字符
        username = username.trim();
        password = password.trim();


        JSONTokenUsernamePasswordAuthenticationToken authRequest =
                new JSONTokenUsernamePasswordAuthenticationToken(username, password, clientCode);

        authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

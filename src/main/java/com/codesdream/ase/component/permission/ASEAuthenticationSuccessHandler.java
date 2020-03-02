package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.respond.UserLoginCheckerJSONRespond;
import com.codesdream.ase.model.permission.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

// 认证成功返回
@Slf4j
@Component
public class ASEAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Resource
    private JSONParameter jsonParameter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        // 对AJAX登录请求特殊化处理
        if(Optional.ofNullable(request.getHeader("X-Requested-With")).isPresent()) {
            HttpSession session = request.getSession();
            SecurityContext securityContext = SecurityContextHolder.getContext();

            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        }

        // 打印用户登录成功日志
        log.info(String.format("ASEAuthenticationSuccessHandler: %s Login Success.",
                ((User)authentication.getDetails()).getUsername()));

        UserLoginCheckerJSONRespond respond = new UserLoginCheckerJSONRespond();
        respond.setUserExist(authentication.isAuthenticated());
        respond.setLoginStatus(authentication.isAuthenticated());

        // 获得session id
        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) (authentication.getDetails());
        respond.setSessionId(webAuthenticationDetails.getSessionId());

        response.getWriter().write(jsonParameter.getJSONString(respond));

    }
}

package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.auth.JSONTokenAuthenticationToken;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.component.json.respond.UserLoginCheckerJSONRespond;
import com.codesdream.ase.model.permission.User;

import com.codesdream.ase.service.IAuthService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

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

    @Resource
    private IAuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {

        UserLoginCheckerJSONRespond respond = new UserLoginCheckerJSONRespond();
        respond.setUserExist(authentication.isAuthenticated());
        respond.setLoginStatus(authentication.isAuthenticated());
        respond.setRespondInformation("Authentication Success");

        // 获得 JSONTokenAuthenticationToken
        JSONTokenAuthenticationToken authenticationToken = (JSONTokenAuthenticationToken) authentication;

        User user = (User) authenticationToken.getPrincipal();

        Optional<String> tokenOptional = authService.userNewTokenGetter(
                user.getUsername(), authenticationToken.getClientCode());

        if(tokenOptional.isPresent()){
            respond.setToken(tokenOptional.get());
        }
        else respond.setToken("");

        response.getWriter().write(jsonParameter.getJSONStandardRespond200(respond));

    }
}

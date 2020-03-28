package com.codesdream.ase.component.auth;

import com.codesdream.ase.component.api.QuickJSONRespond;
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
import java.io.IOException;
import java.util.Optional;

// 认证成功返回
@Slf4j
@Component
public class ASEAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Resource
    private QuickJSONRespond quickJSONRespond;

    @Resource
    private IAuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {

        UserLoginCheckerJSONRespond respond = new UserLoginCheckerJSONRespond();
        respond.setUserExist(authentication.isAuthenticated());
        respond.setLoginStatus(authentication.isAuthenticated());
        respond.setPvc(authService.preValidationCodeGetter());

        // 获得 JSONTokenAuthenticationToken
        JSONTokenAuthenticationToken authenticationToken = (JSONTokenAuthenticationToken) authentication;

        User user = (User) authenticationToken.getPrincipal();

        Optional<String> tokenOptional = authService.userNewTokenGetter(
                user.getUsername(), authenticationToken.getClientCode());

        if(tokenOptional.isPresent()){
            respond.setToken(tokenOptional.get());
        }
        else respond.setToken("");

        // 认证成功返回200
        response.getWriter().write(quickJSONRespond.getRespond200("Authentication Success", respond));

    }
}

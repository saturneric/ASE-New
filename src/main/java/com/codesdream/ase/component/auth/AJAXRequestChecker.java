package com.codesdream.ase.component.auth;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class AJAXRequestChecker {
    public boolean checkAjaxPOSTRequest(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader("X-Requested-With")).isPresent();
    }
}

package com.codesdream.ase.exception;

import org.springframework.security.core.AuthenticationException;

public class JSONTokenExpiredException extends AuthenticationException {
    public JSONTokenExpiredException(String msg) {
        super(msg);
    }
}

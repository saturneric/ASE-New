package com.codesdream.ase.exception;

import org.springframework.security.core.AuthenticationException;

public class JSONTokenIncorrectSignedException extends AuthenticationException {

    public JSONTokenIncorrectSignedException(String msg) {
        super(msg);
    }
}

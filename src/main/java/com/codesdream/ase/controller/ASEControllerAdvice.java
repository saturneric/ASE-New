package com.codesdream.ase.controller;

import com.codesdream.ase.component.error.ErrorResponse;
import com.codesdream.ase.component.json.respond.ErrorInfoJSONRespond;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ASEControllerAdvice {
    @ExceptionHandler(value = {RuntimeException.class})
    public final Object handleRuntimeException(RuntimeException e, WebRequest webRequest){
        ErrorInfoJSONRespond errorInfoJSONRespond = new ErrorInfoJSONRespond();
        errorInfoJSONRespond.setDate(new Date());
        errorInfoJSONRespond.setExceptionMessage(e.getMessage());
        errorInfoJSONRespond.setException(e.getClass().getName());
        return errorInfoJSONRespond;
    }



}

package com.codesdream.ase.controller;

import com.codesdream.ase.component.api.QuickJSONRespond;
import com.codesdream.ase.component.error.ErrorResponse;
import com.codesdream.ase.component.json.respond.ErrorInfoJSONRespond;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.sun.xml.bind.v2.model.annotation.Quick;
import org.apache.poi.openxml4j.opc.internal.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ASEControllerAdvice {

    @Resource
    private QuickJSONRespond quickJSONRespond;

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex) {

        String json = quickJSONRespond.getRespond400(null, getJSONRespondObject(ex));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(Exception ex) {
        String json = quickJSONRespond.getRespond404(null, getJSONRespondObject(ex));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
    }

    private Object getJSONRespondObject(Exception ex){
        ErrorInfoJSONRespond errorInfoJSONRespond = new ErrorInfoJSONRespond();
        errorInfoJSONRespond.setException(ex.getClass().getName());
        errorInfoJSONRespond.setExceptionMessage(ex.getMessage());
        errorInfoJSONRespond.setDate(new Date());
        return errorInfoJSONRespond;
    }


}

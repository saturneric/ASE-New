package com.codesdream.ase.controller;

import com.codesdream.ase.component.api.QuickJSONRespond;
import com.codesdream.ase.component.json.respond.ErrorInfoJSONRespond;
import com.codesdream.ase.exception.badrequest.AlreadyExistException;
import com.codesdream.ase.exception.badrequest.IllegalException;
import com.codesdream.ase.exception.conflict.RelatedObjectsExistException;
import com.codesdream.ase.exception.innerservererror.FormatException;
import com.codesdream.ase.exception.innerservererror.HandlingErrorsException;
import com.codesdream.ase.exception.innerservererror.RuntimeIOException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.util.Date;

@RestControllerAdvice
public class ASEControllerAdvice {

    @Resource
    private QuickJSONRespond quickJSONRespond;

    @ExceptionHandler(value = {
            NullPointerException.class,
            AlreadyExistException.class,
            IllegalException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex) {
        return getResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(Exception ex) {

        return getResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(value = {})
    public ResponseEntity<Object> handleNotAcceptable(Exception ex) {
        return getResponse(HttpStatus.NOT_ACCEPTABLE, ex);
    }

    @ExceptionHandler(value = {RelatedObjectsExistException.class})
    public ResponseEntity<Object> handleConflict(Exception ex) {
        return getResponse(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler(value = {
            HandlingErrorsException.class,
            FormatException.class,
            RuntimeIOException.class})
    public ResponseEntity<Object> handleInnerServerError(Exception ex){
        return getResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<Object> getResponse(HttpStatus status, Exception ex){
        return ResponseEntity.status(status).body(getJSON(status, ex));

    }

    private String getJSON(HttpStatus status, Exception ex){
        return quickJSONRespond.getJSONStandardRespond(status, getJSONRespondObject(ex));
    }

    private Object getJSONRespondObject(Exception ex){
        ErrorInfoJSONRespond errorInfoJSONRespond = new ErrorInfoJSONRespond();
        errorInfoJSONRespond.setException(ex.getClass().getName());
        errorInfoJSONRespond.setExceptionMessage(ex.getMessage());
        errorInfoJSONRespond.setDate(new Date());
        return errorInfoJSONRespond;
    }


}

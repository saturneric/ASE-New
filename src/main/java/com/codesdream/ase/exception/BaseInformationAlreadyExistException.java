package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInformationAlreadyExistException extends RuntimeException {
    private String className;

    public BaseInformationAlreadyExistException(Class<?> aClass){
        super();
        this.className = aClass.getName();
    }
}

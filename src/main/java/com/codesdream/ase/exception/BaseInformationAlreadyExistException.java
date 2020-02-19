package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInformationAlreadyExistException extends RuntimeException {
    private String className;
    private String value;

    public BaseInformationAlreadyExistException(Class<?> aClass, String value){
        super();
        this.className = aClass.getName();
        this.value = value;
    }
}

package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInformationNotExistException extends RuntimeException {
    private String className;
    private String value;

    public BaseInformationNotExistException(Class<?> baseInformationClass, String value){
        super();
        this.className = baseInformationClass.getName();
        this.value = value;
    }
}

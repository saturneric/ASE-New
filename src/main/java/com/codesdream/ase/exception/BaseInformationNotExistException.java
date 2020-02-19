package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInformationNotExistException extends RuntimeException {
    private String className;

    public BaseInformationNotExistException(Class<?> baseInformationClass){
        super();
        this.className = baseInformationClass.getName();
    }
}

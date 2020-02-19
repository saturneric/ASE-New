package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInformationIllegalException extends RuntimeException {
    String type;
    String value;

    public BaseInformationIllegalException(Class<?> aClass, String value){
        super();
        this.type = aClass.getName();
        this.value = value;
    }
}

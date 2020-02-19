package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseInformationIllegalException extends RuntimeException {
    String type;
    String value;

    public BaseInformationIllegalException(String type, String value){
        super();
        this.type = type;
        this.value = value;
    }
}

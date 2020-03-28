package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataInvalidFormatException extends RuntimeException {
    String information;

    public DataInvalidFormatException(Exception e){
        super();
        information = e.getMessage();
    }

    public DataInvalidFormatException(){
        super();
    }
}

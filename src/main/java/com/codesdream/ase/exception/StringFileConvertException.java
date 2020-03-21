package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StringFileConvertException extends RuntimeException {
    public StringFileConvertException(String msg){
        super(msg);
    }
}

package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataFileNotFoundException extends RuntimeException {
    private String path;

    public DataFileNotFoundException(String filePath){
        super();
        this.path = filePath;
    }
}

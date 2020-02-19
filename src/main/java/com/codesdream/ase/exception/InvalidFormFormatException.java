package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.criteria.CriteriaBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidFormFormatException extends Throwable {

    private String message = "";

    public InvalidFormFormatException(){
        super();
    }

    public InvalidFormFormatException(String message){
        this.message = message;
    }
}

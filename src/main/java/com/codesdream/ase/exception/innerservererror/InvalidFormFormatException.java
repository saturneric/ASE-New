package com.codesdream.ase.exception.innerservererror;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.criteria.CriteriaBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidFormFormatException extends FormatException {

    private String message = "Invalid form format";

    public InvalidFormFormatException(){
        super();
    }

    public InvalidFormFormatException(String message){
        this.message = message;
    }
}

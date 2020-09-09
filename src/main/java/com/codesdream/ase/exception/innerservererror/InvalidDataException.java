package com.codesdream.ase.exception.innerservererror;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InvalidDataException extends FormatException{
    private String message;
    public InvalidDataException(String message){
        super(message);
        this.message = message;
    }
}

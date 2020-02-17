package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsernameAlreadyExistException extends RuntimeException {

    String username;

    public UsernameAlreadyExistException(String username){
        super();
        this.username = username;
    }
}

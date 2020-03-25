package com.codesdream.ase.exception.badrequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsernameAlreadyExistException extends AlreadyExistException {

    String username;

    public UsernameAlreadyExistException(String username){
        super(username);
        this.username = username;
    }
}

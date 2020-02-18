package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotFoundException extends RuntimeException {
    Integer id;
    String username;

    public UserNotFoundException(Integer id, String username){
        super();
        this.id = id;
        this.username = username;
    }
}

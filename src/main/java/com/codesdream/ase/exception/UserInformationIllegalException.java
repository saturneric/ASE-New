package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserInformationIllegalException extends RuntimeException {

    String username;

    public UserInformationIllegalException(String username){
        super();
        this.username = username;
    }

}

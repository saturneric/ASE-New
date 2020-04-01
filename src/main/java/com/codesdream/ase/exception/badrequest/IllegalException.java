package com.codesdream.ase.exception.badrequest;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalException extends RuntimeException {
    public IllegalException(String msg){
        super(msg);
    }
}

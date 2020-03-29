package com.codesdream.ase.exception.innerservererror;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RuntimeIOException extends RuntimeException {
    public RuntimeIOException(String msg){
        super(msg);
    }
}

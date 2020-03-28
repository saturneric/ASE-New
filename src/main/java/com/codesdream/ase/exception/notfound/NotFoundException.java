package com.codesdream.ase.exception.notfound;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg){
        super(msg);
    }

    public  NotFoundException(){
        super();
    }
}

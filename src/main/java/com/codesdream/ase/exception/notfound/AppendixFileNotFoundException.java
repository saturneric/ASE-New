package com.codesdream.ase.exception.notfound;

import com.codesdream.ase.model.activity.AppendixFile;

import java.io.IOException;

public class AppendixFileNotFoundException extends IOException {
    public int id;
    public int type;
    public static final int ID_NOT_FOUND = 1, FILE_NOT_fOUND = 2, STREAM_FAILURE = 3;

    public AppendixFileNotFoundException(String msg,int id,int type){
        super(msg);
        this.id = id;
        this.type = type;
    }
}

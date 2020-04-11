package com.codesdream.ase.exception.conflict;

public class FileNameConflict extends RuntimeException {
    public String conflictName;
    public FileNameConflict(String msg,String conflictName)
    {
        super(msg);
        this.conflictName = conflictName;
    }

}

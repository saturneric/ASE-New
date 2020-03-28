package com.codesdream.ase.exception.notfound;


public class TagNotFoundException extends NotFoundException {
    String tagName;

    public TagNotFoundException(String tagName){
        super(tagName);
        this.tagName = tagName;
    }
}

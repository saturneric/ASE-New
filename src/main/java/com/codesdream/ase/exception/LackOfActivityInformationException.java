package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LackOfActivityInformationException extends Throwable {
    private String message = "";

    public LackOfActivityInformationException() {
        super();
    }

    public LackOfActivityInformationException(String message) {
        this.message = message;
    }
}

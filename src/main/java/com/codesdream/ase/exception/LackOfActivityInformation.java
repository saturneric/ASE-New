package com.codesdream.ase.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LackOfActivityInformation extends Throwable {
    private String message = "";

    public LackOfActivityInformation(){super();}

    public LackOfActivityInformation(String message){this.message = message;}
}

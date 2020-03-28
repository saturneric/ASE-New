package com.codesdream.ase.component.json.respond;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorInfoJSONRespond {
    String exception = null;
    String exceptionMessage = null;
    Date date = null;
}

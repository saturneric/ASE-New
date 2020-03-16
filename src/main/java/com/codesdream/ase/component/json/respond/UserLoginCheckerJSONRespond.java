package com.codesdream.ase.component.json.respond;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserLoginCheckerJSONRespond {
    Boolean userExist = null;
    Boolean userBanned = null;
    Boolean loginStatus = null;
    String respondInformation = "";
    String token = null;

}

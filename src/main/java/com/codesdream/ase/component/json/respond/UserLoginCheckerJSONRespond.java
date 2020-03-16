package com.codesdream.ase.component.json.respond;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class UserLoginCheckerJSONRespond {
    boolean userExist = false;
    boolean loginStatus = false;
    boolean userBanned = false;
    String respondInformation = "";
    String token = "";

}

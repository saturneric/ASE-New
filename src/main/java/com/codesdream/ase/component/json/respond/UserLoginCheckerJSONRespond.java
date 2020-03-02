package com.codesdream.ase.component.json.respond;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginCheckerJSONRespond extends JSONBaseRespondObject {
    boolean userExist = false;
    boolean loginStatus = false;
    boolean userBanned = false;
    String respondInformation = "";
    String sessionId = "";

    public UserLoginCheckerJSONRespond(){
        super("success");
    }

}

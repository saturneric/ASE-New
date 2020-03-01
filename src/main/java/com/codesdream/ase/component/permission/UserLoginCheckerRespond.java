package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.datamanager.RespondJSONBaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginCheckerRespond extends RespondJSONBaseObject {
    boolean userExist = false;
    boolean loginStatus = false;
    boolean userBanned = false;
    String respondInformation = "";

    public UserLoginCheckerRespond(){
        super("success");
    }

}

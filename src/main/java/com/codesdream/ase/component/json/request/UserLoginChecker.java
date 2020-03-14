package com.codesdream.ase.component.json.request;

import lombok.Data;

@Data
public class UserLoginChecker {
    private String checkType;
    private String username;
    private String password;
}

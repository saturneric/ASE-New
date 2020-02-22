package com.codesdream.ase.component.permission;

import lombok.Data;

@Data
public class UserLoginChecker {
    private String checkType;
    private String username;
    private String password;
}

package com.codesdream.ase.component.json.request;

import lombok.Data;

@Data
public class UserLoginChecker {
    // 请求类型
    private String checkType;
    private String username;
    private String password;
    private String clientCode;

}

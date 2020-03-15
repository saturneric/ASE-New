package com.codesdream.ase.component.json.request;

import lombok.Data;

@Data
public class UserLoginChecker {
    // 请求类型
    private String checkType;
    private String username;
    private String password;

//    // 客户端类型
//    private String clientType;
//    // JSON签名
//    private String signed;
}

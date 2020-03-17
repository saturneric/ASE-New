package com.codesdream.ase.component.json.request;

import lombok.Data;

@Data
public class UserRegisterChecker {

    // 学号
    private String studentId;

    // 密码
    private String password;

    // 密保问题
    private String userQuestion;

    // 密保答案
    private String userAnswer;
}

package com.codesdream.ase.model.permission;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_auth")
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 密保问题
    private String user_question;

    // 密保问题答案
    private String user_answer;

    // 用户邮箱
    private String mail;
}

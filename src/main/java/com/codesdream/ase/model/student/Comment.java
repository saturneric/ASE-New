package com.codesdream.ase.model.student;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    // 发出此评论的用户
    int userId;

    // 评论内容
    String context;
}

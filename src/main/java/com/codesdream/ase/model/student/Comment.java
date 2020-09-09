package com.codesdream.ase.model.student;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    // 发出此评论的用户
    String userId;

    // 评论内容
    String context;

    // 评论时间
    Date date = new Date();
}

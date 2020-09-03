package com.codesdream.ase.model.student;

import com.codesdream.ase.model.file.Image;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// 动态/说说
@Entity
@Table
public class Moment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    int userId;
    // 动态内容
    String description;

    // 点赞计数
    AtomicInteger likeCount = new AtomicInteger(0);

    // 评论
    @OneToMany(cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.MERGE)
    List<Image> images = new ArrayList<>();
}
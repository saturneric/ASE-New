package com.codesdream.ase.model.achievement;

import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.model.permission.UserDetail;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "award")

public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 标题
    private String title = "";

    // 描述
    private String description = "";

    // 分类
    private String type = "";

    // 加分
    private int bonus;


}

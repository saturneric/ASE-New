package com.codesdream.ase.model.parent;

import com.codesdream.ase.model.file.Image;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Excercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    // 开始时间
    Date start;

    // 持续时间
    Time duration;

    // 学期
    int term;
}

package com.codesdream.ase.model.parent;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table
@Data
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    int studentId;

    // 开始时间
    Date start;

    // 持续时间
    Time duration;

    // 学期
    int term;
}

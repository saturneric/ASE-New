package com.codesdream.ase.model.achievement;

import com.codesdream.ase.model.activity.Period;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "exam_result")

public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 课程名称
    @Column(name = "subject", nullable = false)
    private String subject = "";

    // 课程学分
    @Column(name = "credit", nullable = false)
    private float credit;

    // 课程成绩
    @Column(name = "score", nullable = true)
    private int score;

    // 课程绩点
    @Column(name = "grade_point", nullable = true)
    private float gradePoint;


    public float getCredit() {
        return credit;
    }

    public int getScore() {
        return score;
    }

}

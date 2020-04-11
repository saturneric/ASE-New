package com.codesdream.ase.model.achievement;

import com.codesdream.ase.model.activity.Period;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "student_score_report")

public class StudentScoreReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 课程-得分集合
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<ExamResult> examResults = new HashSet<>();

    // 个人学分积
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private AccumulatedGPA accumulatedGPA;

}

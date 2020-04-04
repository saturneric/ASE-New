package com.codesdream.ase.model.achievement;

import com.codesdream.ase.model.activity.Period;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Data
@Entity
@Table(name = "accumulated_gpa")

public class AccumulatedGPA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 课程-得分集合
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<ExamResult> examResults = new HashSet<>();

    // 个人学分积
    @JoinColumn(nullable = true)
    private float accumulatedGPA;


    //除数为零exception待加
    public AccumulatedGPA(Set<ExamResult> initExamResults) {
        int totalProduct = 0, totalCredit = 0;
        for(ExamResult er : initExamResults){
            totalProduct += er.getCredit() * er.getScore();
            totalCredit += er.getCredit();
        }
        this.accumulatedGPA = totalProduct / totalCredit;
    }


}

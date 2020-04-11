package com.codesdream.ase.model.achievement;

import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.model.permission.UserDetail;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "comprehensive_evaluation")

public class ComprehensiveEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 学分积
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AccumulatedGPA accumulatedGPA;

    // G2项得分
    private int g2;

    // G3项得分
    private int g3;

    // G4项得分
    private int g4;

    // G5项得分
    private int g5;

    // G6项得分
    private int g6;

    // 获奖
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private Set<Award> awards = new HashSet<>();

}

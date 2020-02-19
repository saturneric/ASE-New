package com.codesdream.ase.model.information;

import lombok.Data;

import javax.persistence.*;

/**
 * 学生基本真实信息
 */
@Data
@Entity
@Table(name = "base_student_info")
public class BaseStudentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    // 真实姓名
    private String name = "";
    // 学号
    private String studentId = "";
    // 班号
    private String classId = "";
    // 性别
    private String sex = "";
    // 民族
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseEthnic ethnic = null;
    // 学院
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseCollege college = null;
    // 专业
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseMajor major = null;
    // 政治面貌
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BasePoliticalStatus politicalStatus = null;
    // 省份地区
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseAdministrativeDivision administrativeDivision = null;

}

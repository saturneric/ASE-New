package com.codesdream.ase.model.permission;

import com.codesdream.ase.model.file.Image;
import com.codesdream.ase.model.information.*;
import lombok.Data;

import javax.persistence.*;

/**
 * 用户相关详细信息
 */
@Data
@Entity
@Table(name = "user_detail")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    // 学生班号
    private String classId = null;

    // 所属地区
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseAdministrativeDivision baseAdministrativeDivision = null;

    // 所属学院
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseCollege baseCollege = null;

    // 所属专业
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseMajor baseMajor = null;

    // 民族
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseEthnic baseEthnic = null;

    // 政治面貌
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BasePoliticalStatus basePoliticalStatus = null;

    // 真实姓名
    private String realName = null;

    // 性别
    private String sex = null;

    // 在校认证状态
    private boolean atSchool = false;

    // 电话
    private String telNum;

    // 邮箱
    private String email;

    @OneToOne
    private Image profilePic;
}

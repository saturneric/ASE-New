package com.codesdream.ase.model.permission;

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

    // 所属地区
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseAdministrativeDivision baseAdministrativeDivision;

    // 所属学院
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseCollege baseCollege;

    // 所属专业
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseMajor baseMajor;

    // 民族
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BaseEthnic baseEthnic;

    // 政治面貌
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private BasePoliticalStatus basePoliticalStatus;

    // 真实姓名
    private String realName = "";

    // 在校状态
    private boolean atSchool = false;
}

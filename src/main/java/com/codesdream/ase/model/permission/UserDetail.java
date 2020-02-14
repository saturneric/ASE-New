package com.codesdream.ase.model.permission;

import com.codesdream.ase.model.information.BaseAdministrativeDivision;
import com.codesdream.ase.model.information.BaseCollege;
import com.codesdream.ase.model.information.BaseMajor;
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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BaseAdministrativeDivision baseAdministrativeDivision;

    // 所属学院
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BaseCollege baseCollege;

    // 所属专业
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BaseMajor baseMajor;
}

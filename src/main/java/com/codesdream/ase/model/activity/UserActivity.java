package com.codesdream.ase.model.activity;

import com.codesdream.ase.model.permission.User;
import javafx.scene.chart.ScatterChart;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user_act")
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //关联的用户
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "userActivity")
    @JoinColumn(nullable = false, unique = true)
    private User user;

    //主要负责的活动
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Activity> mainResponsibleActs;

    //次要负责的活动
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Activity> secondaryResponsibleActs;

    //可见的活动
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Activity> visibleActs;

    //创建的活动
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Activity> createdActs;

    //可报名的活动
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Activity> signUpActs;

    //已经参与的活动
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Activity> participatedActs;

    //正在（将要）参与的活动
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Activity> participatingActs;

    //打卡签到活动
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Activity> clockIns;
}

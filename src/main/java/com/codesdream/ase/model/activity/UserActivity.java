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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userActivity")
    @JoinColumn(nullable = false)
    private User user;

    //主要负责的活动
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> manageActivities;

    //次要负责的活动
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> assistActivities;

    //可见的活动
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> visibleActivities;

    //创建的活动
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> createdActivities;

    //可报名的活动
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> signUpActivities;

    //已经参与的活动
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> participatedActivities;

    //正在（将要）参与的活动
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> participatingActivities;

    //打卡签到活动
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> clockIn;
}

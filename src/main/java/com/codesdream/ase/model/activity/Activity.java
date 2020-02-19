package com.codesdream.ase.model.activity;

import com.codesdream.ase.model.permission.User;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //活动标题
    @Column(name = "title", nullable = false)
    private String title;

    //创建人
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    private User creator;

    //活动类型
    @Column(nullable = false)
    private String type;

    //活动描述
    @Column
    private String description;

    //活动周期，格式：阿拉伯数字数字+单位，0表示无周期
    @Column
    private String cycle;

    //自愿参与人列表
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "act_volunteer")
    private Set<User> volunteers;

    //参与人列表
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "act_participate",
            joinColumns = {
                @JoinColumn(name = "act_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "participate_id")
            }
    )
    private Set<User> participateGroup;

    //实际参与人列表
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "act_participated",
            joinColumns = {
                @JoinColumn(name = "act_id" )
            },
            inverseJoinColumns = {
                @JoinColumn(name = "participated_id")
            }
    )
    private Set<User> participatedGroup;

    //可报名人员列表
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "act_sign",
            joinColumns = {
                @JoinColumn(name = "act_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "sign_id")
            }
    )
    private Set<User> signGroup;

    //已报名人员列表
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "act_signed",
            joinColumns = {
                @JoinColumn(name = "act_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "signed_id")
            }
    )
    private Set<User> signedGroup;

    //可见人员列表
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "act_vis",
            joinColumns = {
                @JoinColumn(name = "act_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "vis_id")
            }
    )
    private Set<User> visibleGroup;

    //通知人员列表
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "act_inform",
            joinColumns = {
                @JoinColumn(name = "act_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "inform_id")
            }
    )
    private Set<User> informGroup;

    //已通知人员列表
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "act_informed",
            joinColumns = {
                @JoinColumn(name = "act_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "informed_id")
            }
    )
    private Set<User> informedGroup;

    //计划开始时间
    @OneToOne(cascade = CascadeType.ALL)
    private Period planPeriod;

    //实际开始时间
    @OneToOne(cascade = CascadeType.ALL)
    private Period realPeriod;

    //提前提醒时间
    @Column(name = "remind_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date remindTime;

    //附件组(名字)
    @ElementCollection(targetClass = java.lang.String.class)
    private List<String> enclosures;

    //主要负责人
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User chiefManager;

    //次要负责人
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "act_id")
    private Set<User> assistManagers;

    //是否开始
    @Column//(name = "is_on", nullable = false)
    boolean isOn;

    //是否结束
    @Column//(name = "is_off", nullable = false)
    boolean isOff;

    //考勤安排
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Attendance attendance;

    //活动报告
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Report report;

    public Activity(){
        initDefault();
    }

    private void initDefault(){
        this.title = "";
        this.creator = new User();
        this.type = "";
        this.description = "";
        this.cycle = "";
        this.volunteers = new HashSet<>();
        this.participateGroup = new HashSet<>();
        this.participatedGroup = new HashSet<>();
        this.participatedGroup = new HashSet<>();
        this.signGroup = new HashSet<>();
        this.signedGroup = new HashSet<>();
        this.visibleGroup = new HashSet<>();
        this.informGroup = new HashSet<>();
        this.informedGroup = new HashSet<>();
        this.planPeriod = new Period();
        this.realPeriod = new Period();
        this.enclosures = new ArrayList<>();
        this.chiefManager = new User();
        this.assistManagers = new HashSet<>();
        this.isOn = false;
        this.isOff = false;
    }
}
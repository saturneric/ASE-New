package com.codesdream.ase.model.activity;

import com.codesdream.ase.model.permission.User;
import lombok.Data;

import javax.persistence.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "report",
        indexes = {
            @Index(name = "report_index1", columnList = "title"),
            @Index(name = "report_index2", columnList = "creator")
        }
)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //活动标题
    @Column(nullable = false, unique = true)
    private String title;

    //创建人
    @Column(nullable = false)
    private String creator;

    //活动类型
    @Column(nullable = false)
    private String type;

    //活动描述
    @Column(nullable = true)
    private String description;

    //活动周期，格式：阿拉伯数字数字+单位，0表示无周期
    @Column(nullable = true)
    private String cycle;

    //自愿参与人列表
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "act_volunteer")
    private Set<User> volunteers;

    //参与人列表
    @ManyToMany(cascade = CascadeType.ALL)
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
    @ManyToMany(cascade = CascadeType.ALL)
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
    @ManyToMany(cascade = CascadeType.ALL)
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
    @ManyToMany(cascade = CascadeType.ALL)
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
    @ManyToMany(cascade = CascadeType.ALL)
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
    @ManyToMany(cascade = CascadeType.ALL)
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
    @ManyToMany(cascade = CascadeType.ALL)
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
    @JoinColumn(name = "plan_period")
    private Period planPeriod;

    //实际开始时间
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "real_period")
    private Period realPeriod;

    //主要负责人
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chief_manager", nullable = false)
    private User chiefManager;

    //次要负责人
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "act_id")
    private List<User> assistManagers;


    //考勤安排
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance")
    private Attendance attendance;

    //活动笔记
    @Column(name = "notes")
    private String notes;

    //图表
    @ElementCollection(targetClass = java.lang.String.class)
    @Column(name = "charts")
    private List<String> charts;

    //附件url
    @Column(name = "enclosure")
    private String enclosure;

    //活动
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "activity")
    private Activity activity;
}

package com.codesdream.ase.model.leaves;

import com.codesdream.ase.model.permission.User;
import lombok.Data;
import java.util.*;
import javax.persistence.*;

@Data
@Entity
@Table(name = "leaves")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    //发出人
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User userFrom;
    //审批人容器
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set <User> userTo;
    //请假原因
    @Column
    private String reasonToLeave;


    //请假类型 病假，事假等
    @Column(nullable = false)
    private String type;
    //批准状态
    @Column(nullable = false)
    private String authentication;

    //审核备注
    @Column
    private String comment;
    //开始时间
    @Column(nullable = false)
    private Date startTime;

    @Column(nullable = false)
    private Date endTime;
    //申请时间
    private Date applyTime;
    //认证时间
    private Date authTime;

}

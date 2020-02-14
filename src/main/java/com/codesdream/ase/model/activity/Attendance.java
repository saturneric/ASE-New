package com.codesdream.ase.model.activity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //二维码url
    @Column(name = "qr_code", nullable = false, unique = true)
    private String qrCode;

    //是否在线
    @Column(name = "is_online", nullable = false)
    private boolean isOnline;

    //打卡时段列表
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "attendance")
    @JoinColumn(name = "a_id")
    private Set<Period> clockInPeriods;

    //打卡方式，0表示被扫，1表示扫
    @Column(nullable = false)
    private boolean means;
}

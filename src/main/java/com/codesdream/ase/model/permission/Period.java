package com.codesdream.ase.model.permission;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "period_container")
@Data
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //开始时间
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    //结束时间
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    //启用状体
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    public Period(Date startTime, Date endTime, boolean enabled) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.enabled = enabled;
    }

}

package com.codesdream.ase.model.activity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "start_time")//, nullable = false)
    private LocalDateTime startTime = LocalDateTime.of(2020,2,18,16,36);

    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "end_time")//, nullable = false)
    private LocalDateTime endTime = LocalDateTime.of(2020,2,18,16,37);

    //启用状态
    @Column(name = "enabled")//, nullable = false)
    private boolean enabled;

    public Period(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Period(){

    }

}

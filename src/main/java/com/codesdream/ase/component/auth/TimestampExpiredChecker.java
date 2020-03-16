package com.codesdream.ase.component.auth;

import org.springframework.stereotype.Component;

import java.util.Date;

// 验证时间戳是否有效
@Component
public class TimestampExpiredChecker {

    public boolean checkTimestampBeforeMaxTime(String timestamp, int seconds){
        Date timestampDate = new Date(Long.parseLong(timestamp));
        long currentTime = System.currentTimeMillis();
        Date maxDate = new Date(currentTime + seconds * 1000);
        return timestampDate.before(maxDate);
    }

}

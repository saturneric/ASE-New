package com.codesdream.ase.component.auth;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

// 随机特征码生成器
@Component
public class JSONRandomCodeGenerator {
    @Resource
    private SHA1Encoder encoder;

    public String generateRandomCode(String username, Date date, String clientCode){
        return encoder.encode(String.format("RandomCode [%s][%s][%s]",
                username, Long.toString(date.getTime()), clientCode));
    }
}

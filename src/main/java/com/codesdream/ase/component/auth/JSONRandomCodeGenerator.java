package com.codesdream.ase.component.auth;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class JSONRandomCodeGenerator {
    @Resource
    private SHA1Encoder encoder;

    public String generateRandomCode(String username, Date date, String apiSHA1){
        return encoder.encode(String.format("RandomCode [%s][%s][%s]",
                username, date.toString(), apiSHA1));
    }
}

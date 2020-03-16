package com.codesdream.ase.component.auth;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// 用来给JSON生成签名
@Component
public class JSONSignedGenerator {
    @Resource
    SHA1Encoder encoder;

    public String generateSigned(String username, String randomCode, String token){
        return encoder.encode(String.format("SIGN [%s][%s][%s]",username, randomCode, token));
    }
}

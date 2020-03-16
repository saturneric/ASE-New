package com.codesdream.ase.component.auth;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Component
public class AuthTokenGenerator {
    @Resource
    private SHA1Encoder encoder;

    public String generateAuthToken(String username){
        Date dateNow = new Date();
        UUID uuid = UUID.randomUUID();
        return encoder.encode(String.format("Token [%s][%d][%s]",username,dateNow.getTime(), uuid.toString()));
    }
}

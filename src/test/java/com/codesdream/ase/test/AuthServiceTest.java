package com.codesdream.ase.test;

import com.codesdream.ase.service.AuthService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {

    @Resource
    private AuthService authService;

    // 测试随机验证码
    @Test
    public void preValidationCodeCheckerTest(){
        String authStr = authService.preValidationCodeGetter();

        Assert.assertTrue(authService.preValidationCodeChecker(authStr));
    }
}

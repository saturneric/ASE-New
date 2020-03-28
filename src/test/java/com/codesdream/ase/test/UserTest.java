package com.codesdream.ase.test;

import com.codesdream.ase.model.permission.User;

import static org.junit.Assert.*;

import com.codesdream.ase.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * 用户基本表单元测试
 * 用于测试数据库与DAO层交互是否通畅
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserTest {

    @Resource
    private UserService userService;

    /**
     * 基本存储与查询测试
     */
    @Test
    public void UserBaseTest_1(){
        User user = userService.getDefaultUser();
        user.setUsername("Tim");
        user.setPassword("123456");
        user.getUserAuth().setStudentID("2018303026");
        user.getUserAuth().setMail("937447984@qq.com");
        user.getUserAuth().setUserQuestion("Your favourite animal?");
        user.getUserAuth().setUserAnswer("Cat");
        user.getUserDetail().setAtSchool(true);
        user.getUserDetail().setRealName("提姆");
        userService.save(user);

        Optional<User> userOptional = userService.findUserByUsername("Tim");

        assertTrue(userOptional.isPresent());
        user = userOptional.get();

        assertEquals(user.getUsername(), "Tim");
        assertEquals(user.getPassword(),
                "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");

        // 检查账号状态
        assertTrue(user.isEnabled());
        assertFalse(user.isDeleted());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertEquals(user.getUserAuth().getStudentID(), "2018303026");
        assertEquals(user.getUserAuth().getMail(), "937447984@qq.com");
        assertEquals(user.getUserAuth().getUserQuestion(), "Your favourite animal?");
        assertEquals(user.getUserAuth().getUserAnswer(), "Cat");
    }

    @Test
    public void UserBaseTest_2(){
        Optional<User> userOptional = userService.findUserByUsername("Tim");
        assertTrue(userOptional.isPresent());
        User user = userOptional.get();

        user.setEnabled(false);
        user.getUserAuth().setMail("saturneric@163.com");
        user.getUserDetail().setRealName("张三丰");

        user =  userService.update(user);

        assertEquals(user.getUserAuth().getMail(), "saturneric@163.com");
        assertEquals(user.getUserDetail().getRealName(), "张三丰");
        assertFalse(user.isEnabled());

    }
}

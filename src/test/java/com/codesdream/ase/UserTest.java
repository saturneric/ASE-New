package com.codesdream.ase;

import com.codesdream.ase.model.permission.User;

import static org.junit.Assert.*;

import com.codesdream.ase.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 用户基本表单元测试
 * 用于测试数据库与DAO层交互是否通畅
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    private UserService userService;

    /**
     * 基本存储与查询测试
     */
    @Test
    public void UserBaseTest_1(){
        // 查找数据库中是否有重复项
        Pair<Boolean, User> checker = userService.checkIfUserExists("Tim");
        if(checker.getKey()){
            userService.delete(checker.getValue());
        }

        User user = userService.getDefaultUser();
        user.setUsername("Tim");
        user.setPassword("123456");
        user.getUserAuth().setStudentID("2018303026");
        user.getUserAuth().setMail("937447984@qq.com");
        user.getUserAuth().setUserQuestion("Your favourite animal?");
        user.getUserAuth().setUserAnswer("Cat");
        user.getUserDetail().setAtSchool(true);
        userService.save(user);

        user = userService.findUserByUsername("Tim");

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
        User user = userService.findUserByUsername("Tim");



    }
}

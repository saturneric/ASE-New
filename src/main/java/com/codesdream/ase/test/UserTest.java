package com.codesdream.ase.test;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.UserRepository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 用户基本表单元测试
 * 用于测试数据库与DAO层交互是否通畅
 */
@DataJpaTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    /**
     * 基本存储与查询测试
     */
    @Test
    public void UserBaseTest_1(){
        User user = new User("Tim", "123456");
        userRepository.save(user);
        assertTrue(userRepository.findByUsername("Tim").isPresent());
        assertFalse(userRepository.findByUsername("Tom").isPresent());
        user = userRepository.findByUsername("Tim").get();

        assertEquals(user.getUsername(), "Tim");
        assertEquals(user.getPassword(), "123456");
        assertTrue(user.isEnabled());
        assertFalse(user.isDeleted());
    }

    @Test
    public void UserBaseTest_2(){
        // 用户
        User user = new User("Pat", "123456");
        // 标签
        Tag tag = new Tag("学生","普通学生");
        // 功能性权限容器
        FunctionalPermissionContainer functionalPermissionContainer =
                new FunctionalPermissionContainer("基本用户权限", "基本的用户权限");

        // 添加为标签功能性权限容器
        HashSet<FunctionalPermissionContainer> functionalPermissionContainers = new HashSet<>();
        functionalPermissionContainers.add(functionalPermissionContainer);
        // 等待添加

        // 为用户添加标签
        HashSet<Tag> tags = new HashSet<>();
        tags.add(tag);
        user.setTags(tags);

        userRepository.save(user);




    }
}

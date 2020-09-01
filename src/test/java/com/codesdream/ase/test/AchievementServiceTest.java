package com.codesdream.ase.test;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.Report;
import com.codesdream.ase.service.ActivityService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 成绩管理子系统单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

public class AchievementServiceTest {

    @Resource
    private AchievementService achievementService;


}

package com.codesdream.ase.test;

import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.component.datamanager.DataModelRepositorySearcher;
import com.codesdream.ase.component.datamanager.DataModelSearcher;
import com.codesdream.ase.repository.permission.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 测试DataModel相关查找器
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataManagerTest {
    @Resource
    ASESpringUtil springUtil;

    @Test
    public void dataModelSearcherTest() {
        DataModelSearcher dataModelSearcher = springUtil.getBean(DataModelSearcher.class);
        dataModelSearcher.getDataModelClass("permission", "Tag");
        Assert.assertTrue(dataModelSearcher.isPresent());

        for(String param : dataModelSearcher.getDataModelParamArgs()){
            System.out.println(param);
        }

    }

    @Test
    public void  dataModelRepositorySearcherTest(){
        DataModelRepositorySearcher dataModelRepositorySearcher =
                springUtil.getBean(DataModelRepositorySearcher.class);

        dataModelRepositorySearcher.getDataModelRepositoryClass("permission", "User");
        Assert.assertTrue(dataModelRepositorySearcher.isPresent());
        UserRepository userRepository = dataModelRepositorySearcher.getDataModelRepositoryInstance();

    }

}

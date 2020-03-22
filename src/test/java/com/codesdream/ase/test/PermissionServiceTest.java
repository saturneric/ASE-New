package com.codesdream.ase.test;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.PermissionContainersCollection;
import com.codesdream.ase.model.permission.ScopePermissionContainer;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.service.IPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class PermissionServiceTest {

    @Resource
    private IPermissionService permissionService;

    @Test
    public void PermissionServiceBaseTest(){
        FunctionalPermissionContainer fpc = permissionService.getDefaultFPC("活动管理权");
        fpc = permissionService.save(fpc);

        Tag tag1 = permissionService.getDefaultTag("九班班长"),
                tag2 = permissionService.getDefaultTag("九班班委"),
                tag3 = permissionService.getDefaultTag("九班普通学生");
        tag1 = permissionService.save(tag1);
        tag2 = permissionService.save(tag2);
        tag3 = permissionService.save(tag3);

        ScopePermissionContainer spc = permissionService.getDefaultSPC("九班全体学生");
        spc = permissionService.save(spc);

        PermissionContainersCollection pcc = permissionService.getDefaultPCC("九班班长权限容器集合");
        pcc = permissionService.save(pcc);

        // 给活动管理权赋予添加/参与/管理的权力
        fpc = permissionService.addRoleToFPC(fpc, "activity_create");
        fpc = permissionService.addRoleToFPC(fpc, "activity_participate");
        fpc = permissionService.addRoleToFPC(fpc, "activity_manage");

        // 把九班班委加入到九班全体学生中
        spc = permissionService.addTagToSPC(spc, tag2);
        // 将九班普通学生加入到九班全体学生中
        spc = permissionService.addTagToSPC(spc, tag3);

        // 把活动管理权赋予范围九班全体学生,加入到九班班长权限容器集合中
        pcc = permissionService.addRelationItemToPCC(pcc, fpc, spc);
        // 将设置好的权限容器集合赋予九班班长
        tag1 = permissionService.addPCCToTag(tag1, pcc);
    }

    @Test
    public void PermissionServiceBaseTest2() {

    }
}

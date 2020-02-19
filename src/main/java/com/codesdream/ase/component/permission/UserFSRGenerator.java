package com.codesdream.ase.component.permission;

import com.codesdream.ase.model.permission.FunctionalScopeRelation;
import com.codesdream.ase.model.permission.PermissionContainersCollection;
import com.codesdream.ase.model.permission.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 生成功能性权限容器与范围性权限容器关联对列表
 */
@Component
public class UserFSRGenerator {

    @Resource
    private UserPCCListGenerator userPCCListGenerator;


    public Collection<FunctionalScopeRelation> generateFSRs(
            Collection<PermissionContainersCollection> pccs){
        Collection<FunctionalScopeRelation> fsrs =
                new ArrayList<>();

        for(PermissionContainersCollection pcc : pccs){
            fsrs.addAll(pcc.getFunctionalScopeRelations());
        }

        return fsrs;
    }

    public Collection<FunctionalScopeRelation> generateFSRs(
            User user){
        return generateFSRs(
                userPCCListGenerator.generatePCCs(user));
    }

    public Collection<FunctionalScopeRelation> generateFSRs(
            String username){
        return generateFSRs(
                userPCCListGenerator.generatePCCs(username));
    }
}

package com.codesdream.ase.component.permission;

import com.codesdream.ase.model.permission.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 生成功能性权限容器列表
 */
@Component
public class UserFPCListGenerator {
    @Resource
    private UserFSRGenerator fsrListGenerator;

    public Collection<FunctionalPermissionContainer> generateFPC(
            Collection<FunctionalScopeRelation> functionalScopeRelations){
        Collection<FunctionalPermissionContainer> functionalPermissionContainers
                = new ArrayList<>();

        for (FunctionalScopeRelation functionalScopeRelation : functionalScopeRelations){
            functionalPermissionContainers.add(functionalScopeRelation.getFunctionalPermissionContainer());
        }

        return functionalPermissionContainers;
    }

    public Collection<FunctionalPermissionContainer> generateFPCs(User user){
        return generateFPC(
                fsrListGenerator.generateFSRs(user)
        );
    }

    public Collection<FunctionalPermissionContainer> generateFPCs(String username){
        return generateFPC(
                fsrListGenerator.generateFSRs(username)
        );
    }
}

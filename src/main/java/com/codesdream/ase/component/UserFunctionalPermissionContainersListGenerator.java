package com.codesdream.ase.component;

import com.codesdream.ase.model.permission.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 生成功能性权限容器列表
 */
public class UserFunctionalPermissionContainersListGenerator {

    private UserFunctionalScopeRelationsListGenerator functionalScopeRelationsListGenerator;

    public UserFunctionalPermissionContainersListGenerator(
            UserFunctionalScopeRelationsListGenerator userFunctionalScopeRelationsListGenerator)
    {
        if(userFunctionalScopeRelationsListGenerator == null) throw new NullPointerException();
        this.functionalScopeRelationsListGenerator = userFunctionalScopeRelationsListGenerator;
    }

    public Collection<FunctionalPermissionContainer> generateFunctionalContainers(
            Collection<FunctionalScopeRelation> functionalScopeRelations){
        Collection<FunctionalPermissionContainer> functionalPermissionContainers
                = new ArrayList<>();

        for (FunctionalScopeRelation functionalScopeRelation : functionalScopeRelations){
            functionalPermissionContainers.add(functionalScopeRelation.getFunctionalPermissionContainer());
        }

        return functionalPermissionContainers;
    }

    public Collection<FunctionalPermissionContainer> generateFunctionalPermissionContainers(User user){
        return generateFunctionalContainers(
                functionalScopeRelationsListGenerator.generateFunctionalScopeRelations(user)
        );
    }

    public Collection<FunctionalPermissionContainer> generateFunctionalPermissionContainers(String username){
        return generateFunctionalContainers(
                functionalScopeRelationsListGenerator.generateFunctionalScopeRelations(username)
        );
    }
}

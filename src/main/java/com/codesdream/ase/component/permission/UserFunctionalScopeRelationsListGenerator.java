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
public class UserFunctionalScopeRelationsListGenerator {

    @Resource
    private UserPermissionContainersCollectionsListGenerator userPermissionContainersCollectionsListGenerator;


    public Collection<FunctionalScopeRelation> generateFunctionalScopeRelations(
            Collection<PermissionContainersCollection> permissionContainersCollections){
        Collection<FunctionalScopeRelation> functionalScopeRelations =
                new ArrayList<>();

        for(PermissionContainersCollection permissionContainersCollection : permissionContainersCollections){
            functionalScopeRelations.addAll(permissionContainersCollection.getFunctionalScopeRelations());
        }

        return functionalScopeRelations;
    }

    public Collection<FunctionalScopeRelation> generateFunctionalScopeRelations(
            User user){
        return generateFunctionalScopeRelations(
                userPermissionContainersCollectionsListGenerator.generatePermissionContainerCollections(user));
    }

    public Collection<FunctionalScopeRelation> generateFunctionalScopeRelations(
            String username){
        return generateFunctionalScopeRelations(
                userPermissionContainersCollectionsListGenerator.generatePermissionContainerCollections(username));
    }
}

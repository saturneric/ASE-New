package com.codesdream.ase.component.permission;

import com.codesdream.ase.model.permission.PermissionContainersCollection;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 生成权限容器集合列表
 */
@Component
public class UserPermissionContainersCollectionsListGenerator {
    @Resource
    private UserTagsListGenerator userTagsListGenerator;

    public Collection<PermissionContainersCollection> generatePermissionContainerCollections(
            Collection<Tag> tags){
        Collection<PermissionContainersCollection> permissionContainersCollections =
                new ArrayList<>();
        for(Tag tag : tags){
            permissionContainersCollections.addAll(tag.getPermissionContainersCollections());
        }
        return permissionContainersCollections;
    }

    public Collection<PermissionContainersCollection> generatePermissionContainerCollections(
            User user) {
        return generatePermissionContainerCollections(userTagsListGenerator.generateTags(user));
    }

    public Collection<PermissionContainersCollection> generatePermissionContainerCollections(
            String username){
        return generatePermissionContainerCollections(userTagsListGenerator.generateTags(username));
    }
}

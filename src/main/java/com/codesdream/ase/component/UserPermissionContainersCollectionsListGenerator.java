package com.codesdream.ase.component;

import com.codesdream.ase.model.permission.PermissionContainersCollection;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 生成权限容器集合列表
 */
public class UserPermissionContainersCollectionsListGenerator {

    private UserTagsListGenerator userTagsListGenerator;

    public UserPermissionContainersCollectionsListGenerator(UserTagsListGenerator userTagsListGenerator)
    {
        if(userTagsListGenerator == null) throw new NullPointerException();
        this.userTagsListGenerator = userTagsListGenerator;
    }

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

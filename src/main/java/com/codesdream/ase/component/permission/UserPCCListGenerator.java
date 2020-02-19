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
public class UserPCCListGenerator {
    @Resource
    private UserTagsListGenerator userTagsListGenerator;

    public Collection<PermissionContainersCollection> generatePCCs(
            Collection<Tag> tags){
        Collection<PermissionContainersCollection> pccs =
                new ArrayList<>();
        for(Tag tag : tags){
            pccs.addAll(tag.getPermissionContainersCollections());
        }
        return pccs;
    }

    public Collection<PermissionContainersCollection> generatePCCs(
            User user) {
        return generatePCCs(userTagsListGenerator.generateTags(user));
    }

    public Collection<PermissionContainersCollection> generatePCCs(
            String username){
        return generatePCCs(userTagsListGenerator.generateTags(username));
    }
}

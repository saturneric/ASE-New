package com.codesdream.ase.component.permission;

import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 生成用户列表
 */
@Component
public class UsersListGenerator {
    public Collection<User> generateUsers(Collection<Tag> tags){
        Collection<User> users = new ArrayList<>();
        for(Tag tag : tags){
            users.addAll(tag.getUsers());
        }
        return users;
    }
}

package com.codesdream.ase.component.permission;

import com.codesdream.ase.model.permission.ScopePermissionContainer;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.permission.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * 生成用户标签列表
 */
@Component
public class UserTagsListGenerator {
    @Resource
    UserRepository userRepository;

    public Collection<Tag> generateTags(ScopePermissionContainer spc){
        return new ArrayList<>(spc.getTags());
    }

    public Collection<Tag> generateTags(User user){
        return new ArrayList<Tag>(user.getTags());
    }

    public Collection<Tag> generateTags(String username){
        Optional<User> user = userRepository.findByUsername(username);
        // 检查用户是否存在
        if(!user.isPresent()) throw new RuntimeException("User Not Found");

        return generateTags(user.get());
    }

    public Collection<String> generateTagsName(User user){
        Collection<String> tagsName = new ArrayList<>();
        Collection<Tag> tags = generateTags(user);
        for(Tag tag : tags){
            tagsName.add(tag.getName());
        }
        return tagsName;
    }

    public Collection<String> generateTagsName(String username){
        Optional<User> user = userRepository.findByUsername(username);

        if(!user.isPresent()) throw new RuntimeException("User Not Found");

        return generateTagsName(user.get());
    }
}

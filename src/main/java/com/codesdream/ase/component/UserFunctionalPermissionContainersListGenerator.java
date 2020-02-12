package com.codesdream.ase.component;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.PermissionContainersCollection;
import com.codesdream.ase.model.permission.Tag;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.UserRepository;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class UserFunctionalPermissionContainersListGenerator {
    @Resource
    UserTagsListGenerator userTagsListGenerator;

    @Resource
    UserRepository userRepository;

    public Collection<FunctionalPermissionContainer> generateFunctionalContainers(User user){
        Collection<Tag> tags = userTagsListGenerator.generateTags(user);
        Collection<FunctionalPermissionContainer> functionalPermissionContainers = new ArrayList<>();
        // 等待添加
        return functionalPermissionContainers;
    }

    public Collection<FunctionalPermissionContainer> generateFunctionalContainers(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()) throw new RuntimeException("User Not Found");
        return generateFunctionalContainers(user.get());
    }
}

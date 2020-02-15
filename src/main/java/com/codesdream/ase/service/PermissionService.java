package com.codesdream.ase.service;

import com.codesdream.ase.component.UserFunctionalPermissionContainersListGenerator;
import com.codesdream.ase.component.UserFunctionalScopeRelationsListGenerator;
import com.codesdream.ase.model.permission.*;
import com.codesdream.ase.repository.FunctionalPermissionRepository;
import com.codesdream.ase.repository.ScopePermissionRepository;
import com.codesdream.ase.repository.TagRepository;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class PermissionService implements IPermissionService {

    @Resource
    private TagRepository tagRepository;

    @Resource
    private FunctionalPermissionRepository functionalPermissionRepository;

    @Resource
    private ScopePermissionRepository scopePermissionRepository;

    @Resource
    private UserFunctionalPermissionContainersListGenerator userFunctionalPermissionContainersListGenerator;

    @Resource
    UserFunctionalScopeRelationsListGenerator userFunctionalScopeRelationsListGenerator;

    @Override
    public Optional<Tag> findTag(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Optional<FunctionalPermissionContainer> findFunctionalPermissionContainer(String name) {
        return functionalPermissionRepository.findByName(name);
    }

    @Override
    public Optional<ScopePermissionContainer> findScopePermissionContainer(String name) {
        return scopePermissionRepository.findByName(name);
    }

    @Override
    public Collection<PermissionContainersCollection> getPermissionContainerCollections(Tag tag) {
        return new ArrayList<>(tag.getPermissionContainersCollections());
    }

    @Override
    public Collection<Tag> getTagsFromScopePermissionContainers(ScopePermissionContainer scopePermissionContainer) {
        return new ArrayList<>(scopePermissionContainer.getTags());
    }

    @Override
    public Collection<Tag> getTagsFromUser(User user) {
        return new ArrayList<>(user.getTags());
    }

    @Override
    public Collection<FunctionalPermissionContainer> getFunctionPermissionContainers(PermissionContainersCollection permissionContainersCollection) {

        Collection<PermissionContainersCollection> permissionContainersCollections = new ArrayList<PermissionContainersCollection>(){{
            add(permissionContainersCollection);
        }};

        // 生成功能性与范围性权限容器关联对
        Collection<FunctionalScopeRelation> functionalScopeRelations =
                userFunctionalScopeRelationsListGenerator.generateFunctionalScopeRelations(permissionContainersCollections);
        return userFunctionalPermissionContainersListGenerator.generateFunctionalContainers(functionalScopeRelations);
    }

    @Override
    public Collection<User> getUsersFromTag(Tag tag) {
        return null;
    }

    @Override
    public void addRelationItemToPermissionContainerCollectionPermissionContainerCollection(PermissionContainersCollection permissionContainersCollection, FunctionalPermissionContainer functionalPermissionContainer, ScopePermissionContainer scopePermissionContainer) {

    }

    @Override
    public void addRelationItemsToPermissionContainerCollectionPermissionContainerCollection(PermissionContainersCollection permissionContainersCollection, Collection<Pair<FunctionalPermissionContainer, ScopePermissionContainer>> functionalScopePermissionContainerPairs) {

    }

    @Override
    public void addUserToTag(Tag tag, User user) {

    }

    @Override
    public void addUsersToTag(Tag tag, Collection<User> users) {

    }

    @Override
    public void addRoleToFunctionalPermissionContainer(FunctionalPermissionContainer functionalPermissionContainer, String role) {

    }

    @Override
    public void addRolesToFunctionalPermissionContainer(FunctionalPermissionContainer functionalPermissionContainer, Collection<String> roles) {

    }

    @Override
    public void save(Tag tag) {

    }

    @Override
    public void save(FunctionalPermissionContainer functionalPermissionContainer) {

    }

    @Override
    public void save(ScopePermissionContainer scopePermissionContainer) {

    }
}

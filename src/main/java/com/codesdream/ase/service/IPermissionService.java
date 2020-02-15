package com.codesdream.ase.service;

import com.codesdream.ase.model.permission.*;
import com.sun.org.apache.xpath.internal.functions.FuncQname;
import javafx.util.Pair;

import java.util.Collection;
import java.util.Optional;

public interface IPermissionService {
    // 查找用户标签
    Optional<Tag> findTag(String name);

    // 查找功能性权限容器
    Optional<FunctionalPermissionContainer> findFunctionalPermissionContainer(String name);

    // 查找范围性权限容器
    Optional<ScopePermissionContainer> findScopePermissionContainer(String name);

    // 标签下所有的获得权限容器集合列表
    Collection<PermissionContainersCollection> getPermissionContainerCollections(Tag tag);

    // 获得范围性权限容器下的所有标签列表
    Collection<Tag> getTagsFromScopePermissionContainers(
            ScopePermissionContainer scopePermissionContainer);

    // 查找用户下的所有标签列表
    Collection<Tag> getTagsFromUser(User user);

    // 查找功能性权限容器下的所有范围性权限容器列表
    Collection<FunctionalPermissionContainer> getFunctionPermissionContainers(
            PermissionContainersCollection permissionContainersCollection);

    // 查找标签下的所有用户
    Collection<User> getUsersFromTag(Tag tag);

    // 指定一对功能性权限容器与对应的范围性权限容器并添加到指定权限容器集合中
    void addRelationItemToPermissionContainerCollectionPermissionContainerCollection(
            PermissionContainersCollection permissionContainersCollection,
            FunctionalPermissionContainer functionalPermissionContainer,
            ScopePermissionContainer scopePermissionContainer);

    // 指定多对功能性权限容器与对应的范围性权限容器并添加到指定权限容器集合中
    void addRelationItemsToPermissionContainerCollectionPermissionContainerCollection(
            PermissionContainersCollection permissionContainersCollection,
            Collection<Pair<FunctionalPermissionContainer, ScopePermissionContainer>>
            functionalScopePermissionContainerPairs);

    // 添加一个用户到指定标签中
    void addUserToTag(Tag tag, User user);

    // 添加多个用户到指定标签中
    void addUsersToTag(Tag tag, Collection<User> users);

    // 为功能性权限容器添加一个访问控制角色
    void addRoleToFunctionalPermissionContainer(
            FunctionalPermissionContainer functionalPermissionContainer,
            String role);

    // 为功能性权限容器添加多个访问控制角色
    void addRolesToFunctionalPermissionContainer(
            FunctionalPermissionContainer functionalPermissionContainer,
            Collection<String> roles);

    void save(Tag tag);

    void save(FunctionalPermissionContainer functionalPermissionContainer);

    void save(ScopePermissionContainer scopePermissionContainer);

}

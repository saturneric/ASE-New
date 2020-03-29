package com.codesdream.ase.service;

import com.codesdream.ase.model.permission.*;
import javafx.util.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IPermissionService {

    FunctionalPermissionContainer getDefaultFPC(String name);

    ScopePermissionContainer getDefaultSPC(String name);

    PermissionContainersCollection getDefaultPCC(String name);

    Tag getDefaultTag(String name);

    // 查找用户标签
    Optional<Tag> findTag(String name);

    // 查找用户标签
    Optional<Tag> findTag(Integer id);

    // 列出所有的标签
    Iterable<Tag> findAllTag();

    Set<Tag> findTags(List<String> names);

    // 查找功能性权限容器
    Optional<FunctionalPermissionContainer> findFPC(String name);

    // 查找范围性权限容器
    Optional<ScopePermissionContainer> findSPC(String name);

    // 根据id查找功能性权限容器
    Optional<FunctionalPermissionContainer> findFPC(int id);

    // 根据id查找范围性权限容器
    Optional<ScopePermissionContainer> findSPC(int id);

    // 标签下所有的获得权限容器集合列表
    Collection<PermissionContainersCollection> getPCCs(Tag tag);

    Set<PermissionContainersCollection> findPCCs(Set<Integer> pccs);

    Optional<PermissionContainersCollection> findPCC(Integer id);

    // 获得范围性权限容器下的所有标签列表
    Collection<Tag> getTagsFromSPC(
            ScopePermissionContainer spc);

    // 查找用户下的所有标签列表
    Collection<Tag> getTagsFromUser(User user);


    // 查找功能性权限容器下的所有范围性权限容器列表
    Collection<FunctionalPermissionContainer> getFPCs(
            PermissionContainersCollection pcc);

    // 查找标签下的所有用户
    Set<User> getUsersFromTag(Tag tag);

    // 指定一对功能性权限容器与对应的范围性权限容器并添加到指定权限容器集合中
    PermissionContainersCollection addRelationItemToPCC(
            PermissionContainersCollection pcc,
            FunctionalPermissionContainer fpc,
            ScopePermissionContainer spc);

    // 指定多对功能性权限容器与对应的范围性权限容器并添加到指定权限容器集合中
    PermissionContainersCollection addRelationItemsToPCC(
            PermissionContainersCollection pcc,
            Collection<Pair<FunctionalPermissionContainer, ScopePermissionContainer>>
            fspcPairs);

    // 添加一个用户到指定标签中
    Tag addUserToTag(Tag tag, User user);

    // 添加多个用户到指定标签中
    Tag addUsersToTag(Tag tag, Collection<User> users);

    // 为功能性权限容器添加一个访问控制角色
    FunctionalPermissionContainer addRoleToFPC(
            FunctionalPermissionContainer fpc,
            String role);

    // 为功能性权限容器添加多个访问控制角色
    FunctionalPermissionContainer addRolesToFPC(
            FunctionalPermissionContainer fpc,
            Collection<String> roles);

    // 为范围性权限容器添加一个标签
    ScopePermissionContainer addTagToSPC(ScopePermissionContainer spc, Tag tag);

    // 为范围性权限容器添加多个标签
    ScopePermissionContainer addTagsToSPC(ScopePermissionContainer spc, Collection<Tag> tags);

    // 将一个权限容器集合添加到标签中
    Tag addPCCToTag(Tag tag, PermissionContainersCollection pcc);

    // 将多个权限容器集合添加到标签中
    Tag addPCCsToTag(Tag tag, Collection<PermissionContainersCollection> pccs);

    Tag save(Tag tag);

    void delete(Tag tag);

    FunctionalPermissionContainer save(FunctionalPermissionContainer fpc);

    ScopePermissionContainer save(ScopePermissionContainer spc);

    PermissionContainersCollection save(PermissionContainersCollection pcc);

    Tag update(Tag tag);

    FunctionalPermissionContainer update(FunctionalPermissionContainer fpc);

    ScopePermissionContainer update(ScopePermissionContainer spc);

    PermissionContainersCollection update(PermissionContainersCollection pcc);

}

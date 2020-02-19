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
    Optional<FunctionalPermissionContainer> findFPC(String name);

    // 查找范围性权限容器
    Optional<ScopePermissionContainer> findSPC(String name);

    // 根据id查找功能性权限容器
    Optional<FunctionalPermissionContainer> findFPC(int id);

    // 根据id查找范围性权限容器
    Optional<ScopePermissionContainer> findSPC(int id);

    // 标签下所有的获得权限容器集合列表
    Collection<PermissionContainersCollection> getPCCs(Tag tag);

    // 获得范围性权限容器下的所有标签列表
    Collection<Tag> getTagsFromSPC(
            ScopePermissionContainer spc);

    // 查找用户下的所有标签列表
    Collection<Tag> getTagsFromUser(User user);

    // 查找功能性权限容器下的所有范围性权限容器列表
    Collection<FunctionalPermissionContainer> getFPCs(
            PermissionContainersCollection pcc);

    // 查找标签下的所有用户
    Collection<User> getUsersFromTag(Tag tag);

    // 指定一对功能性权限容器与对应的范围性权限容器并添加到指定权限容器集合中
    void addRelationItemToPCCollection(
            PermissionContainersCollection pcc,
            FunctionalPermissionContainer fpc,
            ScopePermissionContainer spc);

    // 指定多对功能性权限容器与对应的范围性权限容器并添加到指定权限容器集合中
    void addRelationItemsToPCC(
            PermissionContainersCollection pcc,
            Collection<Pair<FunctionalPermissionContainer, ScopePermissionContainer>>
            fspcPairs);

    // 添加一个用户到指定标签中
    void addUserToTag(Tag tag, User user);

    // 添加多个用户到指定标签中
    void addUsersToTag(Tag tag, Collection<User> users);

    // 为功能性权限容器添加一个访问控制角色
    void addRoleToFPC(
            FunctionalPermissionContainer fpc,
            String role);

    // 为功能性权限容器添加多个访问控制角色
    void addRolesToFPC(
            FunctionalPermissionContainer fpc,
            Collection<String> roles);

    void save(Tag tag);

    void save(FunctionalPermissionContainer fpc);

    void save(ScopePermissionContainer spc);

    void save(PermissionContainersCollection pcc);

    void update(FunctionalPermissionContainer fpc);

    void update(ScopePermissionContainer spc);

    void update(PermissionContainersCollection pcc);

}

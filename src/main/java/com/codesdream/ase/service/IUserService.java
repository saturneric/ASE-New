package com.codesdream.ase.service;

import com.codesdream.ase.model.information.BaseStudentInfo;
import com.codesdream.ase.model.permission.User;
import javafx.util.Pair;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface IUserService {

   // 获得一个空的默认用户
   User getDefaultUser();

   // 由学生基本信息生成对应用户
   User createUserByStudentInfo(BaseStudentInfo studentInfo);

   List<User> findAll();

   Optional<User> findUserById(int id);

   Optional<User> findUserByUsername(String username);

   // 查询用户是否存在
   public Pair<Boolean, User> checkIfUserExists(String username);

   // 获得用户所有的权限角色
   Collection<? extends GrantedAuthority> getUserAuthorities(User user);

   // 更新用户的密码
   User updatePassword(User user, String password);

   // 封禁用户
   User disableUser(User user);

   // 根据学号生成随机用户名
   void generateRandomUsernameByStudentID(User user, String id);

   // 更具学号获得对应的用户名
   String getUsernameByStudentId(String studentId);

   // 随机生成一个用户名
   void generateRandomUsername(User user);

   // 注册用户
   User save(User user);

   // 更新用户信息
   User update(User user);

   // 删除用户
   void delete(User user);


}

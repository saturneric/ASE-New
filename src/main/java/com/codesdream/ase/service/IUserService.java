package com.codesdream.ase.service;

import com.codesdream.ase.model.permission.User;
import javafx.util.Pair;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface IUserService {

   // 获得一个空的默认用户
   User getDefaultUser();

   List<User> findAll();

   Optional<User> findUserById(int id);

   User findUserByUsername(String username);

   public Pair<Boolean, User> checkIfUserExists(String username);

   // 获得用户所有的权限角色
   Collection<? extends GrantedAuthority> getUserAuthorities(User user);

   // 更新用户的密码
   void updatePassword(User user, String password);

   // 根据学号生成随机用户名
   void generateRandomUsernameByStudentID(User user, String id);

   // 注册用户
   User save(User user);

   // 更新用户信息
   User update(User user);

   // 删除用户
   void delete(User user);


}

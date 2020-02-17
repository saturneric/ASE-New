package com.codesdream.ase.service;

import com.codesdream.ase.component.permission.ASEPasswordEncoder;
import com.codesdream.ase.component.permission.ASEUsernameEncoder;
import com.codesdream.ase.component.permission.UserRolesListGenerator;
import com.codesdream.ase.exception.UserInformationIllegalException;
import com.codesdream.ase.exception.UserNotFoundException;
import com.codesdream.ase.exception.UsernameAlreadyExistException;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.permission.UserRepository;
import javafx.util.Pair;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Resource
    UserRolesListGenerator userRolesListGenerator;

    @Resource
    UserRepository userRepository;

    @Resource
    ASEPasswordEncoder passwordEncoder;

    @Resource
    ASEUsernameEncoder usernameEncoder;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()) throw new UsernameNotFoundException(username);
        return user.get();
    }

    @Override
    public Pair<Boolean, User> checkIfUserExists(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> new Pair<>(true, value)).orElseGet(() -> new Pair<>(false, null));
    }

    @Override
    public Collection<? extends GrantedAuthority> getUserAuthorities(User user) {
        return userRolesListGenerator.generateRoles(user);
    }

    @Override
    public void updatePassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        update(user);
    }

    @Override
    public void generateRandomUsernameByStudentID(User user, String id) {
        user.getUserAuth().setStudentID(id);
        user.setUsername(usernameEncoder.encode(id));
    }

    @Override
    public User save(User user) {
        // 查找用户名是否已经被注册
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UsernameAlreadyExistException(user.getUsername());

        // 用户信息一般性规范检查
        if(user.getUserAuth().getUserAnswer().length() > 255
                || user.getUserAuth().getUserQuestion().length() > 255
                || user.getUserAuth().getStudentID().length() > 24
                || user.getUserAuth().getMail().length() > 64
                || user.getUserDetail().getRealName().length() > 12)
            throw new UserInformationIllegalException(user.getUsername());

        // 强制以哈希值(sha256)保存密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        // 执行前检查
        if(!userRepository.findById(user.getId()).isPresent())
            throw new UserNotFoundException(user.getId(), user.getUsername());
        return userRepository.save(user);

    }

    @Override
    public void delete(User user) {
        // 执行前检查
        if(!userRepository.findById(user.getId()).isPresent())
            throw new UserNotFoundException(user.getId(), user.getUsername());
        userRepository.delete(user);
    }

    // 获得一个默认初始化的用户对象
    @Override
    public User getDefaultUser() {
        return new User();
    }


}

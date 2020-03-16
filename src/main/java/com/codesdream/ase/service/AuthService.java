package com.codesdream.ase.service;

import com.codesdream.ase.component.auth.AuthTokenGenerator;
import com.codesdream.ase.model.auth.JSONToken;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.repository.auth.JSONTokenRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

// 认证服务
@Service
public class AuthService implements IAuthService {

    @Resource
    private JSONTokenRepository jsonTokenRepository;

    @Resource
    private IUserService userService;

    @Resource
    private AuthTokenGenerator authTokenGenerator;

    @Override
    public Optional<JSONToken> findTokenByUserName(String username) {
        return jsonTokenRepository.findByUsername(username);
    }

    @Override
    public boolean checkTokenIfExpired(JSONToken token) {
        return token.getExpiredDate().compareTo(new Date()) <= 0;
    }

    @Override
    public Optional<String> userNewTokenGetter(String username, String clientCode) {
        Pair<Boolean, User> userPair = userService.checkIfUserExists(username);
        if(userPair.getKey()){
            Optional<JSONToken> jsonTokenOptional = jsonTokenRepository.findByUsername(username);
            JSONToken token = jsonTokenOptional.orElseGet(JSONToken::new);

            // 过期时间设置为三十分钟后
            long currentTime = System.currentTimeMillis();
            currentTime +=30*60*1000;
            token.setExpiredDate(new Date(currentTime));
            token.setToken(authTokenGenerator.generateAuthToken(username));


            // 设置用户名
            token.setUsername(username);
            // 设置客户端代号
            token.setClientCode(clientCode);

            // 在数据库中更新新的token
            token = jsonTokenRepository.save(token);
            return Optional.ofNullable(token.getToken());
        }
        else return Optional.empty();
    }
}

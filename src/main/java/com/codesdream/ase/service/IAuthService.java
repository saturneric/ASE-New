package com.codesdream.ase.service;

import com.codesdream.ase.model.auth.JSONToken;

import java.util.Optional;

public interface IAuthService {
    // 通过用户名查找与对应用户相关联的token
    Optional<JSONToken> findTokenByUserName(String username);

    // 检查token是否过期
    boolean checkTokenIfExpired(JSONToken token);

    // 为用户获得一个新的API Token
    Optional<String> userNewTokenGetter(String username);
}

package com.codesdream.ase.component.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

// 明文用户名密码验证授权柄
public class JSONTokenUsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
    // 用户名
    private String username = null;
    // 明文密码
    private String password = null;
    // 授权柄
    private String clientCode = null;

    public JSONTokenUsernamePasswordAuthenticationToken(String username, String password, String clientCode) {
        super(null);
        this.username = username;
        this.password = password;
        this.clientCode = clientCode;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
    // 扩展接口 获得客户端代码
    public String getClientCode() {
        return clientCode;
    }
}

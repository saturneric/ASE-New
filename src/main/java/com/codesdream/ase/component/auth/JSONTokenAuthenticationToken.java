package com.codesdream.ase.component.auth;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// 关联Token与其他用户的相关数据的认证对象
public class JSONTokenAuthenticationToken extends AbstractAuthenticationToken {
    // token 产生的签名
    String signed = null;
    // 用户名
    Object principal = null;
    // JSON 特征随机代码
    String randomCode = null;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public JSONTokenAuthenticationToken(UserDetails principal, String signed, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.randomCode = null;
        this.signed = signed;
        setAuthenticated(true);
    }

    public JSONTokenAuthenticationToken(String principal, String randomCode, String signed) {
        super(null);
        this.principal = principal;
        this.randomCode = randomCode;
        this.signed = signed;
        setAuthenticated(false);
    }

    @Override
    public String getCredentials() {
        return signed;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }
}

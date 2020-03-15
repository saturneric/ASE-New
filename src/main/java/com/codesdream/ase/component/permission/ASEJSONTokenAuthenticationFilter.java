package com.codesdream.ase.component.permission;

import com.alibaba.fastjson.JSONObject;
import com.codesdream.ase.component.auth.AJAXRequestChecker;
import com.codesdream.ase.component.auth.JSONRandomCodeGenerator;
import com.codesdream.ase.component.auth.JSONSignedGenerator;
import com.codesdream.ase.component.auth.JSONTokenAuthenticationToken;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.exception.JSONTokenExpiredException;
import com.codesdream.ase.exception.JSONTokenIncorrectSignedException;
import com.codesdream.ase.model.auth.JSONToken;
import com.codesdream.ase.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;


// API请求验证过滤
@Slf4j
public class ASEJSONTokenAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JSONParameter jsonParameter;

    @Resource
    private JSONRandomCodeGenerator randomCodeGenerator;

    @Resource
    private AJAXRequestChecker ajaxRequestChecker;

    @Resource
    private AuthService authService;

    @Resource
    private JSONSignedGenerator signedGenerator;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String username = request.getHeader( "username");
        String signed = request.getHeader("signed");
        String rawDate = request.getHeader("date");

        // 对API的内容取哈希码
        String apiSHA1 = request.getHeader("apiSHA1");

        if (signed != null && username != null && rawDate != null && apiSHA1 != null) {
            // 获得具体时间
            Date date = new Date(Long.parseLong(rawDate));

            // 检查时间是否合理
            // ...

            // 生成特征随机代码
            String randomCode = randomCodeGenerator.generateRandomCode(username, date, apiSHA1);

            // 进行验证
            Optional<JSONToken> optionalJSONToken = authService.findTokenByUserName(username);
            if(!optionalJSONToken.isPresent()){
                throw new UserPrincipalNotFoundException("Token Not Found");
            }

            // 检查token是否过期
            JSONToken token = optionalJSONToken.get();
            if(!authService.checkTokenIfExpired(token)) {

                log.info(String.format("Determined Signed: %s",
                        signedGenerator.generateSigned(username, randomCode, token.getToken())));

                // 检查签名是否正确
                if (signed.equals(signedGenerator.generateSigned(username, randomCode, token.getToken()))) {

                    // 查询用户的相关信息
                    UserDetails user = userDetailsService.loadUserByUsername(username);

                    Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

                    // 生成认证柄 (储存上下文信息)
                    JSONTokenAuthenticationToken authentication = new JSONTokenAuthenticationToken(user, signed, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }

        filterChain.doFilter(request, response);

    }

}

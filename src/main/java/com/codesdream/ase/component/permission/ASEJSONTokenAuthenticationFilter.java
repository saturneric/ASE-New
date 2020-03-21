package com.codesdream.ase.component.permission;

import com.codesdream.ase.component.auth.AJAXRequestChecker;
import com.codesdream.ase.component.auth.JSONRandomCodeGenerator;
import com.codesdream.ase.component.auth.JSONSignedGenerator;
import com.codesdream.ase.component.auth.JSONTokenAuthenticationToken;
import com.codesdream.ase.component.datamanager.JSONParameter;
import com.codesdream.ase.model.auth.JSONToken;
import com.codesdream.ase.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;


// API请求验证过滤
@Slf4j
public class ASEJSONTokenAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JSONRandomCodeGenerator randomCodeGenerator;

    @Resource
    private AuthService authService;

    @Resource
    private JSONSignedGenerator signedGenerator;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 用户名
        String username = request.getHeader( "username");
        // 客户端签名
        String signed = request.getHeader("signed");
        // 时间戳
        String timestamp = request.getHeader("timestamp");

        if (signed != null && username != null && timestamp != null) {
            // 获得具体时间
            Date date = new Date(Long.parseLong(timestamp));

            Date now = new Date();

            // 限制时间戳有效区间为60s
            long dtTime = 60*1000;
            Date maxDate = new Date(now.getTime() + dtTime);

            // 检查时间戳是否合理
            if(maxDate.after(date)) {
                // 从服务器中查找token
                Optional<JSONToken> optionalJSONToken = authService.findTokenByUserName(username);
                if (optionalJSONToken.isPresent()) {
                    JSONToken token = optionalJSONToken.get();

                    // 检查token是否过期
                    if (!authService.checkTokenIfExpired(token)) {
                        // 生成特征随机代码
                        String randomCode = randomCodeGenerator.generateRandomCode(username, date, token.getClientCode());

                        log.info(String.format("Determined Signed: %s",
                                signedGenerator.generateSigned(username, randomCode, token.getToken())));

                        // 检查签名是否正确
                        if (signed.equals(signedGenerator.generateSigned(username, randomCode, token.getToken()))) {
                            // 执行授权操作
                            doAuthentication(username, request);
                        }
                    }
                }
            }
        }

        filterChain.doFilter(request, response);

    }

    // 执行授权
    private void doAuthentication(String username, HttpServletRequest request){
        // 查询用户的相关信息
        UserDetails user = userDetailsService.loadUserByUsername(username);

        // 生成用户权限列表
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // 生成授权柄 (储存上下文信息)
        JSONTokenAuthenticationToken authentication =
                new JSONTokenAuthenticationToken(user, null, authorities);

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 执行授权
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}

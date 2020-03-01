package com.codesdream.ase.configure;

import com.codesdream.ase.component.permission.*;
import com.codesdream.ase.service.ASEUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;

/**
 * Spring Security 配置类
 * 用于Spring Security相关参数的配置
 */
@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    ASEUserDetailsService aseUserDetailService;

    @Resource
    ASEPasswordEncoder asePasswordEncoder;

    @Resource
    ASESecurityAuthenticationProvider aseSecurityAuthenticationProvider;

    @Resource
    ASEAuthenticationSuccessHandler successHandler;

    @Resource
    ASEAuthenticationFailureHandler failureHandler;

    @Resource
    ASEAuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    ASEAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .logout().permitAll();

        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

        // 替换掉原有的UsernamePasswordAuthenticationFilter
        http.addFilterBefore(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(aseSecurityAuthenticationProvider)
                .userDetailsService(aseUserDetailService)
                .passwordEncoder(asePasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        "/assets/**",
                        "/register/**",
                        "/forget/**",
                        "/not_found/**",
                        "/error/**",
                        "/login/**");
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    ASEUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
        ASEUsernamePasswordAuthenticationFilter filter = new ASEUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/login/process", "POST"));


        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}

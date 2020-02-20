package com.codesdream.ase.configure;

import com.codesdream.ase.component.permission.ASEPasswordEncoder;
import com.codesdream.ase.component.permission.ASESecurityAuthenticationProvider;
import com.codesdream.ase.service.ASEUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable().formLogin()
                .and()
                .formLogin().loginPage("/login")
                .permitAll().defaultSuccessUrl("/home").permitAll()
                .and()
                .logout().permitAll();

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
                .antMatchers("/assets/**", "/register/**", "/forget/**", "/not_found/**", "/error/**");
    }
}

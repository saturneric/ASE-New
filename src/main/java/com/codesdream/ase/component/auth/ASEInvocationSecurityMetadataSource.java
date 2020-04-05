package com.codesdream.ase.component.auth;

import com.codesdream.ase.model.permission.Function;
import com.codesdream.ase.repository.permission.FunctionRepository;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@Component
public class ASEInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    @Resource
    private FunctionRepository functionRepository;

    private void loadFunctionDefine(){
        map = new HashMap<>();
        Iterable<Function> functions = functionRepository.findAll();
        for(Function function : functions) {
            Collection<ConfigAttribute> array = new ArrayList<>();
            ConfigAttribute cfg = new SecurityConfig(function.getName());

            array.add(cfg);

            map.put(function.getUrl(), array);
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        if(map == null) loadFunctionDefine();

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        for (String url : map.keySet()) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(url);
            if (matcher.matches(request)) {
                return map.get(url);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

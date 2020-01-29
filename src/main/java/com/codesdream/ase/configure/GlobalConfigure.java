package com.codesdream.ase.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class GlobalConfigure {
    @Resource
    AppConfigure appConfigure;

    public AppConfigure getAppConfigure() {
        return appConfigure;
    }

}

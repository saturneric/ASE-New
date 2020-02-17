package com.codesdream.ase.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 手动获得Bean的工具类
 */
@Component
public class ASESpringUtil {
    @Resource
    private ApplicationContext applicationContext;

    public <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }
}

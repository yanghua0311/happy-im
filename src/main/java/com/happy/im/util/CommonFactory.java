package com.happy.im.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by youngwa on 2019/01/14. 21:49
 */
@Component
public class CommonFactory implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<?> clzz) {
        return  (T)applicationContext.getBean(clzz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    public static  ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}


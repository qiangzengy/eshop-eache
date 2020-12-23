package com.qiangzengy.eshop.cache.spring;

import org.springframework.context.ApplicationContext;

/**
 * spring上下文
 */
public class SpringContext {

    private static ApplicationContext applicationContext;

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}

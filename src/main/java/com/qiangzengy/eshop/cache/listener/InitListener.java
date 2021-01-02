package com.qiangzengy.eshop.cache.listener;


import com.qiangzengy.eshop.cache.kafka.KafkaConcusme;
import com.qiangzengy.eshop.cache.spring.SpringContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统初始化监听器
 */
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
        new SpringContext().setApplicationContext(context);
        new Thread(new KafkaConcusme("test-message")).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

package com.example.sb.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2024/1/25 19:43
 */
public abstract class CacheService implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LogManager.getLogger(CacheService.class);
    public abstract void init();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Initializing cache service...");
        init();
    }
}

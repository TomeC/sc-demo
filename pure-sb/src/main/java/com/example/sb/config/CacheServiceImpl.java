package com.example.sb.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2024/1/25 19:49
 */
@Component
public class CacheServiceImpl extends CacheService {
    private static final Logger logger = LogManager.getLogger(CacheServiceImpl.class);

    @Override
    public void init() {
        try {
            Thread.sleep(0); // 模拟缓存更新延迟
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("缓存初始化完成");
    }
}

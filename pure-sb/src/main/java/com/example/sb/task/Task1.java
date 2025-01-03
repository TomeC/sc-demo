package com.example.sb.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2023/11/8 15:01
 */
//@Component
public class Task1 {
    private static final Logger logger = LogManager.getLogger(Task1.class);

    @Scheduled(cron = "0 0/1 * * * ?")
    public void execute() {
        logger.info("task1 execute {}", new Date());
    }
}

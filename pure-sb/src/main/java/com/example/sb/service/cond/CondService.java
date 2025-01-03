package com.example.sb.service.cond;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2024/10/25 18:04
 */
@Service("condService")
@ConditionalOnProperty(name = "cond.service.new.enable", havingValue = "false")
public class CondService {
    private static final Logger logger = LogManager.getLogger(CondService.class);
    public String getName() {
        logger.info("CondService.getName()");
        return "CondService";
    }
}

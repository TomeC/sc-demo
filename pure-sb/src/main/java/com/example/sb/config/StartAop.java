package com.example.sb.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author wangkun1-jk
 * @Description:
 * @date 2024/1/25 19:41
 */
@Aspect
@Component
public class StartAop {
    private static final Logger logger = LogManager.getLogger(StartAop.class);

    @Around("within(com.example.sb.config.CacheService+)")
    public void aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("Before invoking the method={}.{}", proceedingJoinPoint.getThis().getClass().getName(), proceedingJoinPoint.getSignature().getName());
        proceedingJoinPoint.proceed();
        logger.info("End invoking the method={}.{}", proceedingJoinPoint.getThis().getClass().getName(), proceedingJoinPoint.getSignature().getName());
    }

}

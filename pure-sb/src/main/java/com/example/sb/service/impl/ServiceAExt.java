package com.example.sb.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author 王锟
 * @Description:
 * @date 2023/8/25 17:36
 */
@Slf4j
@Service
@Primary
public class ServiceAExt extends ServiceA {
    @Override
    protected void lazyFunction() {
        System.out.println("ServiceAExt lazyFunction");
    }

    @Override
    public void doSomething() {
        log.info("ServiceAExt");
    }
}

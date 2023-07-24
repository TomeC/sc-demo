package com.example.sb.service.impl;

import com.example.sb.service.DaoService;
import com.example.sb.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 王锟
 * @description
 * @date 2023/6/26
 */
@Slf4j
@Service("serviceB")
public class ServiceB implements IService {
    @Resource
    private DaoService daoService;

    @Override
    public void doSomething() {
        daoService.query();
        log.info("ServiceB");
    }
}

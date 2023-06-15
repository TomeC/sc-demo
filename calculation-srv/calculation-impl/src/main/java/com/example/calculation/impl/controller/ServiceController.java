package com.example.calculation.impl.controller;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.registry.NacosRegistration;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ServiceController {
    @Resource
    private NacosServiceManager nacosServiceManager;

    @GetMapping("/service/deRegister")
    public String deRegisterReq() {
       try {
            nacosServiceManager.nacosServiceShutDown();
        } catch (NacosException e) {
            log.error("deregister from nacos error", e);
            return "error\n";
        }
        return "ok\n";
    }
}

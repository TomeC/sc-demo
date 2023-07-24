package com.example.sb.controller;

import com.example.sb.service.IService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 王锟
 * @description
 * @date 2023/6/26
 */
@RestController
public class DemoController {
    @Resource
    private Map<String, IService> serviceMap;

    @GetMapping("/hello")
    public String getHello(@RequestParam("method") String method) {
        serviceMap.get(method).doSomething();
        return "ok\n";
    }
}

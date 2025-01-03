package com.example.sb.controller;

import com.example.sb.service.DemoService;
import com.example.sb.service.cond.CondService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 王锟
 * @description
 * @date 2023/6/26
 */
@RestController
public class DemoController {
    @Resource
    private DemoService service;
    @Resource
    private CondService condService;

    @GetMapping("/hello")
    public String getHello(@RequestParam("method") String method) {
        return condService.getName();
    }
}

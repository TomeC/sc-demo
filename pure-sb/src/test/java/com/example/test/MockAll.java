package com.example.test;

import com.example.sb.PureApplication;
import com.example.sb.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 王锟
 * @description
 * @date 2023/7/7
 */
@SpringBootTest(classes = PureApplication.class)
public class MockAll {
    @Resource
    private DemoService serviceA;

    @Test
    public void test() {
        serviceA.doSomething();
    }
}

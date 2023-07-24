package com.example.test;

import com.example.sb.PureMain;
import com.example.sb.service.IService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 王锟
 * @description
 * @date 2023/7/7
 */
@SpringBootTest(classes = PureMain.class)
public class MockAll {
    @Resource
    private IService serviceA;

    @Test
    public void test() {
        serviceA.doSomething();
    }
}

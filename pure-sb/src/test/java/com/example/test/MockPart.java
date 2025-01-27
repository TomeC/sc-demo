package com.example.test;

import com.example.sb.PureApplication;
import com.example.sb.service.DaoService;
import com.example.sb.service.DemoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.annotation.Resource;

/**
 * @author 王锟
 * @description
 * @date 2023/7/7
 */
@SpringBootTest(classes = PureApplication.class)
public class MockPart {
    @Resource
    private DemoService serviceA;

    @SpyBean
    private DaoService daoService;

    @Test
    public void test() {
        Mockito.when(daoService.save()).thenReturn(1);
        serviceA.doSomething();
    }
}

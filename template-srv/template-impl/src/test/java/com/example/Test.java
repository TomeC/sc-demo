package com.example;

import com.example.template.impl.TemplateImplApplication;
import com.example.template.impl.service.impl.TemplateServiceImpl;
import io.netty.util.internal.InternalThreadLocalMap;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TemplateImplApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Test {
    @Resource
    private TemplateServiceImpl templateService;

    @org.junit.Test
    public void test() {
        templateService.saveTemplate();

    }
}

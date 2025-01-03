package com.example.test;

import com.example.sb.PureApplication;
import com.example.sb.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 王锟
 * @description
 * @date 2023/7/7
 */
@Slf4j
@SpringBootTest(classes = PureApplication.class)
public class ServiceUnionTest {
    @Resource
    private List<DemoService> serviceList;
    private final long time = System.currentTimeMillis();

    public void init() {
        log.info("init once");
    }
    @Test
    public void test() {
        serviceList.forEach(s -> s.doSomething());
        log.info("test time={}", time);
    }
    @Test
    public void test2() {
        log.info("test2 time={}", time);
    }


}

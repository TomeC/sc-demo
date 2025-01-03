package com.example.sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 王锟
 * @description
 * @date 2023/6/26
 */
@ImportResource("classpath*:/spring-xml/**/*.xml")
@EnableScheduling
@SpringBootApplication
public class PureApplication {
    public static void main(String[] args) {
        SpringApplication.run(PureApplication.class, args);
    }
}

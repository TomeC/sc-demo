package com.example.calculation.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class CalculationApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculationApplication.class);
        log.info("CalculationApplication start success");
    }
}

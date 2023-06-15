package com.example.customer;

import com.example.customer.conf.CanaryRuleConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Slf4j
@EntityScan(basePackages = {"com.example.customer.dao.entity"})
@EnableJpaRepositories(basePackages = {"com.example.customer.dao"})
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.example"})
@LoadBalancerClient(value = "template-serv", configuration = CanaryRuleConfiguration.class)
@EnableFeignClients(basePackages = {"com.example.customer.feign"})
public class CustomerImplApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerImplApplication.class);
        log.info("CustomerImplApplication start success");
    }
}

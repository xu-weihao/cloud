package com.test;

import com.netflix.loadbalancer.RandomRule;
import com.rule.RandomRuleConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@RibbonClient(name = "payment-service", configuration = {RandomRuleConfigure.class})
@EnableFeignClients
@EnableHystrix
public class Order80Application {
    public static void main(String[] args) {
        SpringApplication.run(Order80Application.class, args);
    }
}

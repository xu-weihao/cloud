package com.test.zookeeper.controller;

import com.test.common.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String port;
    @Resource
    private DiscoveryClient discoveryClient;

    @RequestMapping("/pay")
    public CommonResult pay(){
        Map<String, Object> data = new HashMap<>();
        data.put("port", port);
        return CommonResult.ok(data);
    }

    @RequestMapping("discovery")
    private void discovery(){
        List<String> services = discoveryClient.getServices();
        services.stream().forEach(s -> {
            System.out.println(s);
        });

        List<ServiceInstance> instances = discoveryClient.getInstances("payment-service");
        instances.stream().forEach(s -> {
            System.out.println(s);
        });
    }
}

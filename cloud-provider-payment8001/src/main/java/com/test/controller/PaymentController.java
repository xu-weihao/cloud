package com.test.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.test.common.CommonResult;
import com.test.common.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payment")
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

    @RequestMapping("/testFeign")
    public CommonResult testFeign(@RequestBody Payment payment) {
        Map<String, Object> data = new HashMap<>();
        data.put("port", port);
        data.put("payment", payment);

        return CommonResult.ok(data);
    }

    @RequestMapping("/payok")
    public CommonResult payok() {
        Map<String, Object> data = new HashMap<>();
        data.put("port", port);
        data.put("type", "payok");

        return CommonResult.ok(data);
    }

    @HystrixCommand(fallbackMethod = "payFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @RequestMapping("/payTimeOut")
    public CommonResult payTimeOut() throws InterruptedException {
        Map<String, Object> data = new HashMap<>();

        TimeUnit.SECONDS.sleep(5);
        System.out.println(123);
        data.put("port", port);
        data.put("type", "支付服务超时正常调用");

        return CommonResult.ok(data);
    }

    public CommonResult payFallback() {
        Map<String, Object> data = new HashMap<>();

        data.put("port", port);
        data.put("type", "支付服务超时fallback 方法调用");

        return CommonResult.ok(data);
    }


    @RequestMapping("/circuitTest")
    @HystrixCommand(fallbackMethod = "circuitTestFallback", commandProperties ={
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    public String circuitTest(int id) {
        if(id < 0){
            int a = 1/0;
        }
        return "服务正常调用，id为：" + id;
    }

    public String circuitTestFallback(int id) {
        return "服务调用失败，id为：" + id;
    }
}

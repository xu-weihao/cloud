package com.test.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.test.common.CommonResult;
import com.test.common.Payment;
import com.test.feign.PaymentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ConsumerController {
    private static final String PAYMENT_SERVICE = "http://payment-service/";
    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private PaymentFeign paymentFeign;

    @RequestMapping("/consume")
    public CommonResult consume(){
        //CommonResult result = restTemplate.getForObject(PAYMENT_SERVICE + "pay", CommonResult.class);
        Payment payment = new Payment();
        payment.setId(2);
        payment.setProduct("aaa");
        payment.setPrice(100);

        return paymentFeign.testFeign(payment);
    }

    @RequestMapping("/consumeOk")
    public CommonResult consumeOk(){
        return paymentFeign.payok();
    }

    @HystrixCommand(fallbackMethod = "payFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    @RequestMapping("/consumeTimeOut")
    public CommonResult consumeTimeOut() throws InterruptedException {
        return paymentFeign.payTimeOut();
    }

    public CommonResult payFallback() {
        Map<String, Object> data = new HashMap<>();

        data.put("type", "消费客户端超时fallback 方法调用");

        return CommonResult.ok(data);
    }
}

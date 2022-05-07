package com.test.controller;

import com.test.common.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    private static final String PAYMENT_SERVICE = "http://payment-service/";
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/consume")
    public CommonResult consume(){
        CommonResult result = restTemplate.getForObject(PAYMENT_SERVICE + "pay", CommonResult.class);

        return result;
    }
}

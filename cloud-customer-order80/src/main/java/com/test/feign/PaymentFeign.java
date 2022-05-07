package com.test.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.test.common.CommonResult;
import com.test.common.Payment;
import com.test.hystrix.PaymentFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(value = "payment-service", fallback = PaymentFeignHystrix.class)
public interface PaymentFeign {
    @RequestMapping("/testFeign")
    public CommonResult testFeign(@RequestBody Payment payment);

    @RequestMapping("/payok")
    public CommonResult payok();

    @RequestMapping("/payTimeOut")
    public CommonResult payTimeOut() throws InterruptedException;

}

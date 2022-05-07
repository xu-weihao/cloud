package com.test.hystrix;

import com.test.common.CommonResult;
import com.test.common.Payment;
import com.test.feign.PaymentFeign;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaymentFeignHystrix implements PaymentFeign {
    @Override
    public CommonResult testFeign(Payment payment) {
        return null;
    }

    @Override
    public CommonResult payok() {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "consume的payok方法调用feign的hystrix");
        return CommonResult.ok(data);
    }

    @Override
    public CommonResult payTimeOut() throws InterruptedException {
        return null;
    }
}

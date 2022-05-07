package com.test;

import com.netflix.hystrix.HystrixMetrics;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrxiConfiguration {
    
    @Bean
    public ServletRegistrationBean hystrixServlet(){
        HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(hystrixMetricsStreamServlet, "/hystrix.stream", "/actuator/hystrix.stream");
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.setName("hystrix.stream");
        return servletRegistrationBean;
    }
}

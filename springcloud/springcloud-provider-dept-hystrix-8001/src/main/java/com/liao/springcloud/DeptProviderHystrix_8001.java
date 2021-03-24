package com.liao.springcloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient   //服务启动自动注册到Eureka
@EnableDiscoveryClient //可以是其他注册中心。
@EnableCircuitBreaker //hystrix开启熔断器
public class DeptProviderHystrix_8001 {
    public static void main(String[] args) {
        SpringApplication.run(DeptProviderHystrix_8001.class, args);
    }
    //增加一个servlet,固定写法，用于监控hystrixDashboard,pom文件需要引入spring-boot-starter-actuator
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        servletRegistrationBean.addUrlMappings("/actuator/hystrix.stream");
        return servletRegistrationBean;
    }
}

package com.liao.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.liao.springcloud"})
@ComponentScan("com.liao.springcloud")
public class SpringcloudConsumerDeptFeign80Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConsumerDeptFeign80Application.class, args);
    }

}

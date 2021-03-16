package com.liao.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {
    /**
     * 配置负载均衡实现restTemplate
     */
    @Bean
    @LoadBalanced//实现负载均衡 Ribbon,基于客户端实现的
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


}

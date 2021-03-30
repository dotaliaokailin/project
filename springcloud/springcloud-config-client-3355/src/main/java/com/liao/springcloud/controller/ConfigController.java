package com.liao.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${eureka.client.service-url.defaultZone}")
    private String eurekaService;
    @Value("${server.port}")
    private String port;
    @Value("${spring.profiles}")
    private String profiles;

    @GetMapping("/config")
    public String getConfig(){
        return "applicationName:"+applicationName+",eurekaService:"+eurekaService+",port:"+port+",profiles:"+profiles;
    }
}

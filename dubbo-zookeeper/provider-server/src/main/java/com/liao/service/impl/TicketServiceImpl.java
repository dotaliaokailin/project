package com.liao.service.impl;

import com.liao.service.TicketService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;


//zookeeper:服务注册与发现
@Service //可以被扫描到，在项目一启动就自动注册到注册中心  注意，这个service是dubbo的service
@Component //在使用dubbo时，这里尽量不要使用@Service 因为dubbo里也有一个service 二者容易混淆
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "ticket";
    }
}

package com.liao.springcloud.controller;

import com.liao.springcloud.pojo.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptComsumerController {
    //Ribbon,这里的地址，应该是一个变量，通过服务名来访问
    //private static final String DEPT_PROVIDER_PREFIX= "http://localhost:8001";
    private static final String DEPT_PROVIDER_PREFIX= "http://SPRINGCLOUD-PROVIDER-DEPT";
    //消费者，不应该有service层
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/comsumer/dept/add")
    public boolean add(Dept dept){
        return restTemplate.postForObject(DEPT_PROVIDER_PREFIX + "/dept/add", dept, Boolean.class);
    }

    @RequestMapping("/comsumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        return restTemplate.getForObject(DEPT_PROVIDER_PREFIX + "/dept/get/{1}", Dept.class, id);
    }

    @RequestMapping("/comsumer/dept/list")
    public List<Dept> queryAll(){
        return restTemplate.getForObject(DEPT_PROVIDER_PREFIX + "/dept/list", List.class);
    }
}

package com.liao.springcloud.controller;

import com.liao.springcloud.pojo.Dept;
import com.liao.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门控制层
 */
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;


    @GetMapping("/dept/get/{id}")
    @HystrixCommand(fallbackMethod = "hystrixGet")
    public Dept get(@PathVariable("id") Long id){
        Dept dept = deptService.queryById(id);
        if(null == dept){
            throw new RuntimeException("id不存在");
        }
        return dept;
    }

    public Dept hystrixGet(@PathVariable("id") Long id){
        return new Dept().setDname("hystrix->id不存在").setDeptno(id).setDb_source("db_source不存在");
    }
}

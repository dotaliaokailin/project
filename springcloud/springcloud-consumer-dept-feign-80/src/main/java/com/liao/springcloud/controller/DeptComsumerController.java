package com.liao.springcloud.controller;

import com.liao.springcloud.pojo.Dept;
import com.liao.springcloud.server.DeptServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class DeptComsumerController {
    //通过feign调用server
    @Autowired
    private DeptServer deptServer;

    @PostMapping("/comsumer/dept/add")
    public boolean add(Dept dept){
        return deptServer.addDept(dept);
    }

    @GetMapping("/comsumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){
        return deptServer.queryById(id);
    }

    @GetMapping("/comsumer/dept/list")
    public List<Dept> queryAll(){
        return deptServer.queryAll();
    }
}

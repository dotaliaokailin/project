package com.liao.springcloud.server;

import com.liao.springcloud.pojo.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Component
@FeignClient(value = "SPRINGCLOUD-PROVIDER-DEPT",fallbackFactory=DeptServerFallbackFactory.class)//eureka注册服务名//fallbackFactory服务降级
public interface DeptServer {
    /**
     * 增加部门
     */
    @PostMapping("/dept/add")
    public boolean addDept(Dept dept);

    /**
     * 通过ID查询部门
     */
    @GetMapping("/dept/get/{id}")
    public Dept queryById(@PathVariable("id") Long id);

    /**
     * 查询所有部门
     */
    @GetMapping("/dept/list")
    public List<Dept> queryAll();
}

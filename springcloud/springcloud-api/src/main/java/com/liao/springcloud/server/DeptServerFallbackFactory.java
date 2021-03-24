package com.liao.springcloud.server;

import com.liao.springcloud.pojo.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeptServerFallbackFactory implements FallbackFactory {
    @Override
    public Object create(Throwable throwable) {
        return new DeptServer() {
            @Override
            public boolean addDept(Dept dept) {
                return false;
            }

            @Override
            public Dept queryById(Long id) {
                return new Dept().setDeptno(id).setDname("服务降级，该服务暂时不能使用。");
            }

            @Override
            public List<Dept> queryAll() {
                return null;
            }
        };
    }
}

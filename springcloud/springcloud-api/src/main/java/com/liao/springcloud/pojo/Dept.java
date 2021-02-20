package com.liao.springcloud.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liao
 * 部门实体类
 */
@Data
@NoArgsConstructor
@Accessors(chain = true) //链式写法
public class Dept implements Serializable {
    /**
     * 部门主键ID
     */
    private Long deptno;
    /**
     * 部门名称
     */
    private String dname;
    /**
     * 资源数据库名称
     * 微服务，一个服务对应一个数据库，同一个信息可能存在不同数据库
     */
    private String db_source;

    public Dept(String dname){
        this.dname = dname;
    }
}

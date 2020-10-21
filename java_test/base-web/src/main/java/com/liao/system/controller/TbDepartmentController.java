package com.liao.system.controller;


import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbDepartment;
import com.liao.system.service.TbDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/system/tb-department")
@Api(value = "部门管理", tags = "部门管理接口")
public class TbDepartmentController {
    @Autowired
    private TbDepartmentService tbDepartmentService;
    
    @ApiOperation(value = "查询部门和人数", notes = "分组查询部门和人数")
    @GetMapping("/findDeptAndCount")
    public Result findDeptAndCount(){
        List<TbDepartment> departments = tbDepartmentService.findDeptAndCount();
        if(null == departments || departments.size() <= 0){
            throw new BusinessException(ResultCodeEnum.DEPARTMENT_NOT_EXIST.getCode(), ResultCodeEnum.DEPARTMENT_NOT_EXIST.getMessage());
        }
        return Result.ok().data("departments", departments);
    }
}


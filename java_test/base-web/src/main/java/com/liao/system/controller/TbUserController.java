package com.liao.system.controller;


import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbUser;
import com.liao.system.service.TbUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/system/tb-user")
@Api(value = "用户模块", tags = "用户接口")
public class TbUserController {
    @Autowired
    private TbUserService tbUserService;

    @GetMapping("/findUsers")
    @ApiOperation(value = "用户列表", notes = "查询所有用户列表")
    public Result findUsers(){
        return Result.ok().data("users", tbUserService.list());
    }

    @GetMapping("/getUserById/{id}")
    @ApiOperation(value = "用户信息", notes = "根据ID查询用户信息")
    public Result getUserById(@PathVariable("id") Long id){
        TbUser user = tbUserService.getById(id);
        if(null != user){
            return Result.ok().data("user", user);
        }else{
            throw new BusinessException(ResultCodeEnum.NO_FOUND_USER_EXCEPTION.getCode(), ResultCodeEnum.NO_FOUND_USER_EXCEPTION.getMessage());
        }
    }

}


package com.liao.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbUser;
import com.liao.system.service.TbUserService;
import com.liao.system.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
//@CrossOrigin//解决跨域注解
public class TbUserController {
    @Autowired
    private TbUserService tbUserService;

    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("/findUsers")
    @ApiOperation(value = "用户列表", notes = "查询所有用户列表")
    public Result findUsers(){
        return Result.ok().data("users", tbUserService.list());
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
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

    /**
     * 分页查询所有用户信息
     * @param currentPage
     * @param pageSize
     * @return
     */
   @GetMapping("/usersPage")
   @ApiOperation(value = "用户分页", notes = "用户分页列表")
   public Result usersPage(@RequestParam(required = true, defaultValue = "1") Integer currentPage, @RequestParam(required = true, defaultValue = "10") Integer pageSize){
       Page<TbUser> page = tbUserService.page(new Page<>(currentPage, pageSize));
       if(null != page){
           return Result.ok().data("total",page.getTotal()).data("userList", page.getRecords());
       }else{
           throw new BusinessException(ResultCodeEnum.NO_FOUND_USER_PAGE_EXCEPTION.getCode(), ResultCodeEnum.NO_FOUND_USER_PAGE_EXCEPTION.getMessage());
       }
   }

    /**
     * 条件分页查询所有用户信息及部门名称
     * @param currentPage
     * @param pageSize
     * @param userVo
     * @return
     */
    @PostMapping("/findUserPage")
    @ApiOperation(value = "用户条件分页", notes = "用户条件分页列表")
    public Result findUserPage(@RequestParam(required = true, defaultValue = "1") Integer currentPage, @RequestParam(required = true, defaultValue = "10") Integer pageSize
            , @RequestBody UserVo userVo){
        System.out.println("currentPage = [" + currentPage + "], pageSize = [" + pageSize + "], userVo = [" + userVo + "]");
        IPage<TbUser> page = tbUserService.findUserPage(currentPage, pageSize, userVo);
        if(null != page){
            return Result.ok().data("total",page.getTotal()).data("userList", page.getRecords());
        }else{
            throw new BusinessException(ResultCodeEnum.NO_FOUND_USER_PAGE_EXCEPTION.getCode(), ResultCodeEnum.NO_FOUND_USER_PAGE_EXCEPTION.getMessage());
        }
    }
}


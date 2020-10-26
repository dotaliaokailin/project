package com.liao.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbRole;
import com.liao.system.service.TbRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/system/tb-role")
public class TbRoleController {
    @Autowired
    private TbRoleService tbRoleService;

    @PostMapping("/findRolePage")
    @ApiOperation(value = "角色分页查询接口", notes = "角色分页查询")
    public Result findRolePage(@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize, @RequestParam("roleName") String roleName){
        Page<TbRole> page = tbRoleService.findRolePage(currentPage, pageSize, roleName);
        if(null != page)
            return Result.ok().data("roleList", page);
        else
            throw new BusinessException(ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getMessage());
    }
}


package com.liao.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbRole;
import com.liao.system.service.TbRoleMenuService;
import com.liao.system.service.TbRoleService;
import com.liao.system.service.TbUserRoleService;
import com.liao.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
@Api(value = "角色模块", tags = "角色接口")
public class TbRoleController {
    @Autowired
    private TbRoleService tbRoleService;
    @Autowired
    private TbRoleMenuService tbRoleMenuService;
    @Autowired
    private TbUserRoleService tbUserRoleService;

    @PostMapping("/findRolePage")
    @ApiOperation(value = "角色分页查询接口", notes = "角色分页查询")
    @PreAuthorize("hasAnyRole('admin', '角色管理员')")
    public Result findRolePage(@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize, @RequestParam("roleName") String roleName){
        Page<TbRole> page = tbRoleService.findRolePage(currentPage, pageSize, roleName);
        if(null != page)
            return Result.ok().data("roleList", page);
        else
            throw new BusinessException(ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getMessage());
    }

    @GetMapping("/findRoleById")
    @ApiOperation(value = "根据ID查询角色信息接口", notes = "根据ID查询角色信息")
    @PreAuthorize("hasAnyRole('admin', '角色管理员')")
    public Result findRoleById(@RequestParam("id") Long id){
        TbRole tbRole = tbRoleService.selectById(id);
        if(null != tbRole)
            return Result.ok().data("role", tbRole);
        else
            throw new BusinessException(ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.ROLE_NOT_FOUND_PAGE.getMessage());
    }

    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "新增修改角色信息接口", notes = "新增修改角色信息")
    @PreAuthorize("hasAnyAuthority('role:add', 'role:update')")
    public Result saveOrUpdate(@RequestBody TbRole tbRole){
        Boolean flag = tbRoleService.saveOrUpdateRole(tbRole);
        if(flag)
            return Result.ok().message(ResultCodeEnum.OP_SUCCESS.getMessage());
        else
            throw new BusinessException(ResultCodeEnum.OP_FAIL.getCode(), ResultCodeEnum.OP_FAIL.getMessage());
    }

    @GetMapping("/deleteRoleById")
    @ApiOperation(value = "根据ID删除角色信息接口", notes = "根据ID删除角色信息")
    @Transactional(propagation = Propagation.NESTED)
    @PreAuthorize("hasAuthority('role:delete')")
    public Result deleteRoleById(@RequestParam("id") Long id){
        boolean flag = tbRoleService.removeById(id);
        if(flag) {
            //删除关联关系表的菜单
            tbRoleMenuService.deleteByRoleId(id);
            //删除关联关系表的人员
            tbUserRoleService.deleteByRoleId(id);
            return Result.ok().code(ResultCodeEnum.DELETE_SUCCESS.getCode()).message(ResultCodeEnum.DELETE_SUCCESS.getMessage());
        }else{
            //设置手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BusinessException(ResultCodeEnum.DELETE_FAIL.getCode(), ResultCodeEnum.DELETE_FAIL.getMessage());
        }
    }

    @GetMapping("/exportExcel")
    @ApiOperation(value = "导出角色信息表Excel", notes = "导出角色信息表Excel接口")
    @PreAuthorize("hasAuthority('role:export')")
    public void exportExcel(HttpServletResponse response){
        try {
            ExcelUtil.download(response, "角色列表", "角色列表" , tbRoleService.exportExcel(), TbRole.class);
        } catch (Exception e){
            throw new BusinessException(ResultCodeEnum.EXPORT_FAIL.getCode(), ResultCodeEnum.EXPORT_FAIL.getMessage());
        }
    }
}


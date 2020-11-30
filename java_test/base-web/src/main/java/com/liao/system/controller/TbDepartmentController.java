package com.liao.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbDepartment;
import com.liao.system.service.TbDepartmentService;
import com.liao.system.service.TbUserService;
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
    @Autowired
    private TbUserService tbUserService;
    
    @ApiOperation(value = "查询部门和人数", notes = "分组查询部门和人数接口")
    @GetMapping("/findDeptAndCount")
    @PreAuthorize("hasAnyRole('admin', '部门管理员')")
    public Result findDeptAndCount(){
        List<TbDepartment> departments = tbDepartmentService.findDeptAndCount();
        if(null == departments || departments.size() <= 0){
            throw new BusinessException(ResultCodeEnum.DEPARTMENT_NOT_EXIST.getCode(), ResultCodeEnum.DEPARTMENT_NOT_EXIST.getMessage());
        }
        return Result.ok().data("departments", departments);
    }

    @ApiOperation(value = "部门分页查询", notes = "部门分页查询接口")
    @GetMapping("/getDeptPage")
    @PreAuthorize("hasAnyRole('admin', '部门管理员')")
    public Result getDeptPage(@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "name", required = false) String name){
        IPage<TbDepartment> deptPage = tbDepartmentService.getDeptPage(currentPage, pageSize, name);
        if(null != deptPage){
            return Result.ok().data("deptList", deptPage);
        }else{
            throw new BusinessException(ResultCodeEnum.DEPARTMENT_NOT_EXIST_PAGE.getCode(), ResultCodeEnum.DEPARTMENT_NOT_EXIST_PAGE.getMessage());
        }
    }

    @ApiOperation(value = "根据ID查询部门信息", notes = "根据ID查询部门信息接口")
    @GetMapping("/getDeptById")
    @PreAuthorize("hasAnyRole('admin', '部门管理员')")
    public Result getDeptById(@RequestParam("id") Long id){
        TbDepartment dept = tbDepartmentService.getById(id);
        if(null != dept){
            return Result.ok().data("dept", dept);
        }else{
            throw new BusinessException(ResultCodeEnum.DEPARTMENT_NOT_EXIST.getCode(), ResultCodeEnum.DEPARTMENT_NOT_EXIST.getMessage());
        }
    }

    @ApiOperation(value = "新增修改部门信息", notes = "新增修改部门信息接口")
    @PostMapping("/saveOrUpdate")
    @PreAuthorize("hasAnyAuthority('department:add', 'department:update')")
    public Result saveOrUpdate(@RequestBody TbDepartment tbDepartment){
        boolean flag = tbDepartmentService.savoOrUpdateDept(tbDepartment);
        if(flag){
            return Result.ok().code(ResultCodeEnum.OP_SUCCESS.getCode()).message(ResultCodeEnum.OP_SUCCESS.getMessage());
        }else{
            throw new BusinessException(ResultCodeEnum.OP_FAIL.getCode(), ResultCodeEnum.OP_FAIL.getMessage());
        }
    }

    @ApiOperation(value = "删除部门信息", notes = "根据ID删除部门信息")
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('department:delete')")
    @Transactional(propagation = Propagation.NESTED)
    public Result delete(@RequestParam("id") Long id){
       try{
           tbDepartmentService.removeById(id);
           tbUserService.resetDeptById(id);
           return Result.ok().code(ResultCodeEnum.DELETE_SUCCESS.getCode()).message(ResultCodeEnum.DELETE_SUCCESS.getMessage());
       }catch (Exception e){
           //设置手动回滚
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
           throw new BusinessException(ResultCodeEnum.DELETE_FAIL.getCode(), ResultCodeEnum.DELETE_FAIL.getMessage());
       }
    }

    @ApiOperation(value = "导出部门信息Excel", notes = "导出部门信息Excel接口")
    @GetMapping("/exportExcel")
    @PreAuthorize("hasAuthority('department:export')")
    public void exportExcel(HttpServletResponse response){
        try {
            ExcelUtil.download(response, "部门列表", "部门列表" , tbDepartmentService.exportExcel(), TbDepartment.class);
        } catch (Exception e){
            throw new BusinessException(ResultCodeEnum.EXPORT_FAIL.getCode(), ResultCodeEnum.EXPORT_FAIL.getMessage());
        }
    }
}


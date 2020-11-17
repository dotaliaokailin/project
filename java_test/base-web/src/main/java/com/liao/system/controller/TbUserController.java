package com.liao.system.controller;


import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbRole;
import com.liao.system.pojo.TbUser;
import com.liao.system.pojo.TbUserRole;
import com.liao.system.service.TbRoleService;
import com.liao.system.service.TbUserRoleService;
import com.liao.system.service.TbUserService;
import com.liao.system.vo.UserVo;
import com.liao.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private TbUserRoleService tbUserRoleService;
    @Autowired
    private TbRoleService tbRoleService;


    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("/findUsers")
    @ApiOperation(value = "用户列表", notes = "查询所有用户列表")
    @PreAuthorize("hasRole('测试用户')")
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
        if(null != user)
            return Result.ok().data("user", user);
        else
            throw new BusinessException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getCode(), ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getMessage());

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
       if(null != page)
           return Result.ok().data("total",page.getTotal()).data("userList", page.getRecords());
       else
           throw new BusinessException(ResultCodeEnum.USER_ACCOUNT_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.USER_ACCOUNT_NOT_FOUND_PAGE.getMessage());

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
        IPage<TbUser> page = tbUserService.findUserPage(currentPage, pageSize, userVo);
        if(null != page)
            return Result.ok().data("total",page.getTotal()).data("userList", page.getRecords());
        else
            throw new BusinessException(ResultCodeEnum.USER_ACCOUNT_NOT_FOUND_PAGE.getCode(), ResultCodeEnum.USER_ACCOUNT_NOT_FOUND_PAGE.getMessage());

    }

    @GetMapping("/findUserById")
    @ApiOperation(value = "根据用户ID查询用户接口", notes = "根据用户ID查询用户")
    public Result findUserById(@RequestParam("id") Long id){
        TbUser tbUser = tbUserService.findUserById(id);
        if(null != tbUser){
            return Result.ok().data("tbUser", tbUser);
        }else {
            throw new BusinessException(ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getCode(), ResultCodeEnum.USER_ACCOUNT_NOT_EXIST.getMessage());
        }
    };

    /**
     * 新增或修改用户
     * @param tbUser
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "用户新增修改", notes = "用户新增修改操作")
    public Result saveOrUpdate(@RequestBody TbUser tbUser){
        boolean flag = tbUserService.saveOrUpdateUser(tbUser);
        if(flag)
            return Result.ok().code(ResultCodeEnum.OP_SUCCESS.getCode()).message(ResultCodeEnum.OP_SUCCESS.getMessage());
        else
            throw new BusinessException(ResultCodeEnum.OP_FAIL.getCode(), ResultCodeEnum.OP_FAIL.getMessage());
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @GetMapping("/deleteUser")
    @ApiOperation(value = "用户删除", notes = "用户删除操作")
    public Result deleteUser(@RequestParam("id") Long id){
        boolean flag = tbUserService.removeById(id);
        if(flag)
            return Result.ok().code(ResultCodeEnum.DELETE_SUCCESS.getCode()).message(ResultCodeEnum.DELETE_SUCCESS.getMessage());
        else
            throw new BusinessException(ResultCodeEnum.DELETE_FAIL.getCode(), ResultCodeEnum.DELETE_FAIL.getMessage());
    }

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     */
    @GetMapping("/download")
    @ApiOperation(value = "导出用户列表", notes = "导出用户列表操作")
    public void download(HttpServletResponse response){
        try {
            ExcelUtil.download(response, "用户列表", "用户列表" , tbUserService.exportUsers(), TbUser.class);
        } catch (Exception e){
            throw new BusinessException(ResultCodeEnum.EXPORT_FAIL.getCode(), ResultCodeEnum.EXPORT_FAIL.getMessage());
        }
    }

    /**
     * 初始化用户角色信息
     */
    @GetMapping("/userRoles")
    @ApiOperation(value = "用户所有的角色", notes = "用户所有的角色的集合")
    public Result userRoles(@RequestParam("id") Long id){
        Map<String, Object> resultMap = new HashMap<>();
        //用户角色表数据转换成long[]数组，用hutool工具转成Long[],Arrays转成List<Long>
        List<Long> roleIds = Arrays.asList(ArrayUtil.wrap(tbUserRoleService.listByUserId(id).stream().mapToLong(ur -> ur.getRoleId()).toArray()));
        //用户拥有的角色信息
        resultMap.put("userRoleIds", roleIds);
        //用户未拥有的角色信息
        resultMap.put("roles", tbRoleService.list());
        return Result.ok().data(resultMap);
    }

    /**
     * 操作用户角色
     */
    @PostMapping("/addUserRoles")
    @ApiOperation(value = "操作用户角色", notes = "操作用户角色接口")
    @Transactional(propagation = Propagation.NESTED)
    public Result addUserRoles(@RequestBody Long[] list, @RequestParam("id") Long id){
        try {
            tbUserRoleService.removeByUserId(id);
            for (int i = 0; i < list.length; i++) {
                tbUserRoleService.save(new TbUserRole(id, list[i]));
            }
        }catch (Exception e){
            //设置手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new BusinessException(ResultCodeEnum.AUTHORIZATION_FAIL.getCode(), ResultCodeEnum.AUTHORIZATION_FAIL.getMessage());
        }
        return Result.ok().code(ResultCodeEnum.AUTHORIZATION_SUCCESS.getCode()).message(ResultCodeEnum.AUTHORIZATION_SUCCESS.getMessage());
    }
}


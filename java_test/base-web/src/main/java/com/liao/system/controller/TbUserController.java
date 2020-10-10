package com.liao.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liao.handler.BusinessException;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbUser;
import com.liao.system.service.TbUserService;
import com.liao.system.util.ExcelUtil;
import com.liao.system.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if(null != user)
            return Result.ok().data("user", user);
        else
            throw new BusinessException(ResultCodeEnum.NO_FOUND_USER_EXCEPTION.getCode(), ResultCodeEnum.NO_FOUND_USER_EXCEPTION.getMessage());

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
           throw new BusinessException(ResultCodeEnum.NO_FOUND_USER_PAGE_EXCEPTION.getCode(), ResultCodeEnum.NO_FOUND_USER_PAGE_EXCEPTION.getMessage());

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
            throw new BusinessException(ResultCodeEnum.NO_FOUND_USER_PAGE_EXCEPTION.getCode(), ResultCodeEnum.NO_FOUND_USER_PAGE_EXCEPTION.getMessage());

    }

    /**
     * 新增或修改用户
     * @param tbUser
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "用户新增修改", notes = "用户新增修改操作")
    public Result saveOrUpdate(@RequestBody TbUser tbUser){
        boolean flag = tbUserService.saveOrUpdate(tbUser);
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
    public void download(HttpServletResponse response) throws IOException {
        ExcelUtil.download(response, "用户列表", "用户列表" , tbUserService.exportUsers(), TbUser.class);
    }
}


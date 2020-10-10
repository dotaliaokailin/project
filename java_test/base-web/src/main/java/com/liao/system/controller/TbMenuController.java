package com.liao.system.controller;


import com.liao.response.Result;
import com.liao.system.pojo.TbMenu;
import com.liao.system.service.TbMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author liao
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/system/tb-menu")
@Api(value = "菜单模块", tags = "菜单接口")
public class TbMenuController {
    @Autowired
    private TbMenuService tbMenuService;

    @GetMapping("/menus")
    @ApiOperation(value = "所有菜单信息", notes = "获取所有菜单信息")
    public Result menus(){
        return Result.ok().data("menus",tbMenuService.list());
    }
}


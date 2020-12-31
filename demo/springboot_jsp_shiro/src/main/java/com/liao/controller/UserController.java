package com.liao.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("findUser")
    //@RequiresRoles("admin")  //基于单个角色
    //@RequiresRoles(value = {"admin","user"})//同时要拥有多个角色
    //@RequiresPermissions("user:select:*") //拥有角色
    public String findUser(){
        return "redirect:/index.jsp";
    }
}

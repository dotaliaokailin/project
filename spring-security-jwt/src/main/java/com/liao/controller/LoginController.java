package com.liao.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 */
@Controller
public class LoginController {


    @RequestMapping("/toMain")
    public String toMain(){
        return "redirect:main.html";
    }

    @RequestMapping("/login")
    public String login() {return "redirect:login.html";}

    @RequestMapping("/loginError")
    public String loginError() {return "redirect:loginError.html";}

    @RequestMapping("/admin")
    //@Secured("ROLE_admin")
    @PreAuthorize("hasRole('admin')")//注解是方法执行前先判断权限用asset表达式，ROLE_开头或者不以ROLE_开头都可以，但是config类上不能加ROLE_开头
    public String admin() {return "redirect:admin.html";}

    @GetMapping("/demo")
    //@ResponseBody
    public String demo(){
        return "demo";
    }

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }
}

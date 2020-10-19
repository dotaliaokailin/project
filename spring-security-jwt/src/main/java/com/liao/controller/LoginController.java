package com.liao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 */
@Controller
public class LoginController {


    @RequestMapping("/toMain")
    public String toLogin(){
        return "redirect:main.html";
    }

    @RequestMapping("/login")
    public String login() {return "redirect:login.html";}

    @RequestMapping("/loginError")
    public String loginError() {return "redirect:loginError.html";}

    @RequestMapping("/admin")
    public String admin() {return "redirect:admin.html";}

    @GetMapping("/demo")
    @ResponseBody
    public String demo(){
        return "demo";
    }
}

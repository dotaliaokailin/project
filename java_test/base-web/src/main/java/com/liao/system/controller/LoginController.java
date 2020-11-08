package com.liao.system.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/")
    public String showHome(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("当前登录者："+ authentication.getName());
        System.out.println("权限："+ authentication.getAuthorities());
        return "home.html";
    }

    @PostMapping("/login")
    public String login(){
        return "redirect:http://localhost:9090/";
    }
}

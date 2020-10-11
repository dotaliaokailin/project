//package com.liao.system.controller;
//
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class LoginController {
//
//    @RequestMapping("/")
//    public String showHome(){
//        String name = SecurityContextHolder.getContext().getAuthentication().getName();
//        System.out.println("当前登录者："+name);
//        return "home.html";
//    }
//
//    @RequestMapping("/login")
//    public String login(){
//        return "login.html";
//    }
//}

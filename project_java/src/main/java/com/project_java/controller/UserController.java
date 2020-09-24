package com.project_java.controller;

import com.project_java.pojo.User;
import com.project_java.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/save")
    public Boolean save(User user){
        return userService.save(user);
    }

    @PostMapping("/login")
    public String[] login(@RequestParam("username") String username, @RequestParam("password") String password){
        User user = userService.login(username, password);
        if(null != user){
            return new String[]{"public","user"};
        }
        return null;
    }
}

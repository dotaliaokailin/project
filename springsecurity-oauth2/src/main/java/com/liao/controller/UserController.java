package com.liao.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * localhost:8080/user/getCurrentUser
     * 请求头添加 Authorization             bearer +token
     * @param authentication
     * @param request
     * @return
     */
    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        String token = authorization.substring(authorization.lastIndexOf("bearer ") + 7);
        return Jwts
                .parser()
                .setSigningKey("salt".getBytes(StandardCharsets.UTF_8)) //设置盐，StandardCharsets.UTF_8解决中文盐乱码问题
                .parseClaimsJws(token)
                .getBody();
        //return authentication.getPrincipal();
    }
}

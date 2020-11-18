package com.liao.auth;

import com.liao.provider.ApplicationContextProvider;
import com.liao.system.pojo.TbUser;
import com.liao.util.JWTTokenUtil;
import com.liao.util.RedisUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyLogoutHandler implements LogoutHandler {
    private RedisUtil redisUtil;
    public MyLogoutHandler(){
        this.redisUtil = ApplicationContextProvider.getBean(RedisUtil.class);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String tokenHeader = request.getHeader(JWTTokenUtil.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JWTTokenUtil.TOKEN_PREFIX)) {
            return;
        }
        String token = tokenHeader.replace(JWTTokenUtil.TOKEN_PREFIX, "");
        boolean hasKey = redisUtil.sHasKey("token", token);
        if(hasKey){
            redisUtil.setRemove("token",token);
        }
    }
}

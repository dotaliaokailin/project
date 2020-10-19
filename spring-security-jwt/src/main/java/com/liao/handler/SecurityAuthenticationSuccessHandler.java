package com.liao.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重写AuthenticationSuccessHandler跳转成功失败页面，原successForwardUrl是转发，想要前后端或者跳百度要重定向
 */
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final String forwardUrl;

    /**
     * @param forwardUrl
     */
    public SecurityAuthenticationSuccessHandler(String forwardUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(forwardUrl),
                () -> "'" + forwardUrl + "' is not a valid forward URL");
        this.forwardUrl = forwardUrl;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //用户
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getPassword() + "--" + user.getUsername() + "--" + user.getAuthorities());
        response.sendRedirect(forwardUrl);
    }
}

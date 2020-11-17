package com.liao.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbUser;
import com.liao.util.JWTTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * jwt过滤认证方式
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //这里要调用/login不然跳不到spring security得login页面
        super.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        //TbUser tbUser = new ObjectMapper().readValue(request.getInputStream(), TbUser.class);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password"), null));
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        TbUser tbUser = (TbUser) authResult.getPrincipal();
        //拿到roles
        StringBuffer roles = new StringBuffer("");
        tbUser.getAuthorities().forEach(role -> roles.append(role.getAuthority()+","));
        String role = roles.substring(0, roles.length() - 2);
        // 根据用户名，角色创建token
        String token = JWTTokenUtil.createToken(tbUser.getUsername(), role, false);
        //设置这个前端才能拿到请求头
        response.setHeader("Access-Control-Expose-Headers", "token");
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader("token", JWTTokenUtil.TOKEN_PREFIX + token);
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");    //设置 HttpServletResponse使用utf-8编码
        response.setHeader("Content-Type", "text/html;charset=utf-8");  //设置响应头的编码
        response.getWriter().write("账号或密码错误");
    }
}

package com.liao.auth;

import com.alibaba.fastjson.JSON;
import com.liao.handler.BusinessException;
import com.liao.provider.ApplicationContextProvider;
import com.liao.response.Result;
import com.liao.response.ResultCodeEnum;
import com.liao.system.pojo.TbUser;
import com.liao.system.service.TbUserService;
import com.liao.util.JWTTokenUtil;
import com.liao.util.RedisUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt授权过滤器
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private RedisUtil redisUtil;
    private TbUserService tbUserService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.redisUtil = ApplicationContextProvider.getBean(RedisUtil.class);
        this.tbUserService = ApplicationContextProvider.getBean(TbUserService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        try {
            String tokenHeader = request.getHeader(JWTTokenUtil.TOKEN_HEADER);
            // 如果请求头中没有Authorization信息则直接放行了
            if (tokenHeader == null || !tokenHeader.startsWith(JWTTokenUtil.TOKEN_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }
            // 如果请求头中有token，则进行解析，并且设置认证信息
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader, response));
            super.doFilterInternal(request, response, chain);
        }catch (Exception e){
            response.setCharacterEncoding("utf-8");    //设置 HttpServletResponse使用utf-8编码
            response.setHeader("Content-Type", "text/html;charset=utf-8");  //设置响应头的编码
            response.getWriter().write(JSON.toJSONString(Result.error().code(ResultCodeEnum.USER_TOKEN_EXPIRATION.getCode()).message(ResultCodeEnum.USER_TOKEN_EXPIRATION.getMessage())));
        }
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader, HttpServletResponse response) {
        String token = tokenHeader.replace(JWTTokenUtil.TOKEN_PREFIX, "");
        String username = JWTTokenUtil.getUsername(token);
        String roles = JWTTokenUtil.getUserRole(token);
        if (username != null){
            //redis判断是否存在该token
            boolean hasToken = redisUtil.sHasKey("token", token);
            if(!hasToken){
                String newToken = JWTTokenUtil.createToken(username, roles, false);
                response.setHeader("Access-Control-Expose-Headers", "token");
                response.setHeader("token", JWTTokenUtil.TOKEN_PREFIX + newToken);
                redisUtil.sSetAndTime("token", 3600 ,newToken);
            }
            TbUser tbUser = tbUserService.selectByUsername(username);
            return new UsernamePasswordAuthenticationToken(username, tbUser.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
        }
        return null;
    }
}

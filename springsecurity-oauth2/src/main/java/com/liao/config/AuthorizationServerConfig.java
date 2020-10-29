package com.liao.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * 授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    /** authorization_code授权码模式:
     * 1 . http://localhost:8080/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://www.baidu.com&scope=all
     *
     *      返回令牌
     *      1-1: Basic Auth 填 下面的 client 和密码 123456
 *          1-2: client_id = 方法里的 client
     *           redirect_uri = 方法里的  重定向地址
     *           scope = 授权范围
     *      1-3: 提交方式 POST  localhost:8080/oauth/token
     *             x-www-form-urlencoded
     *             参数:          值：
     *             grant_type       authorization_code
     *             code             {令牌}
     *             client_id        client
 *                   redirect_uri        http://www.baidu.com
     *             scope            all
     *
     *
     * 2. 调用自己的控制层方法： localhost:8080/user/getCurrentUser
     *      2-1：BearerToken  填上面返回的令牌
     *
     *  password 密码模式：
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()//先放内存中
                .withClient("client")
                //密钥
                .secret(passwordEncoder.encode("123456"))
                //重定向地址
                .redirectUris("http://www.baidu.com")
                //授权范围
                .scopes("all")
                //授权类型
                // authorization_code授权码模式
                // password 密码模式
                .authorizedGrantTypes("authorization_code", "password");
    }

    /**
     * 密码模式:
     *      localhost:8080/oauth/token   提交方式 POST
     *      返回令牌
     *      1-1: Basic Auth 填 下面的 client 和密码 123456
     *          username: UserDetailsService里面的用户名
     *          password: UserDetailsService里面的密码
     *           grant_type =   password
     *           scope = 授权范围
     *      2. 调用自己的控制层方法： localhost:8080/user/getCurrentUser
     *          2-1：BearerToken  填上面返回的令牌
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
    }
}

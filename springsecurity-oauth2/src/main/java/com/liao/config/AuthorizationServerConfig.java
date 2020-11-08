package com.liao.config;

import com.liao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

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
    private UserService userService;

//    @Autowired
//    @Qualifier("redisTokenStore")
//    private TokenStore tokenStore;

    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhance;

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
                //.redirectUris("http://www.baidu.com")
                .redirectUris("http://localhost:8081/login")//重定向回去客户端的登陆页面
                //授权范围
                .scopes("all")
                //设置令牌失效时间 60秒
                .accessTokenValiditySeconds(60)
                //设置刷新令牌失效时间 一天
                .refreshTokenValiditySeconds(86400)
                //自动批准
                .autoApprove(true)
                //授权类型
                // authorization_code授权码模式
                // password 密码模式
                // refresh_token  刷新令牌     重新调用获取令牌 localhost:8080/oauth/token   参数1 grant_type refresh_token    2 refresh_token   刷新令牌token
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");
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
        //设置JWT增强内容
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhance);
        delegates.add(jwtAccessTokenConverter);
        //要把增强内容放进去
        tokenEnhancerChain.setTokenEnhancers(delegates);

        endpoints
                //认证管理
                .authenticationManager(authenticationManager)
                //用户details
                .userDetailsService(userService)
                //Token
                .tokenStore(tokenStore)
                //访问token
                .accessTokenConverter(jwtAccessTokenConverter)
                //设置增强内容
                .tokenEnhancer(tokenEnhancerChain);
    }

    /**
     * 单点登陆必须配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //获取密钥必须身份认证，单点登陆必须配置
        security.tokenKeyAccess("isAuthenticated()");
    }
}

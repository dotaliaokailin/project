package com.liao.config;

import com.liao.handler.SecurityAccessDeniedHandler;
import com.liao.handler.SecurityAuthenticationSuccessHandler;
import com.liao.handler.SecurityAuthenticationFailureHandler;
import com.liao.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * spring security配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityAccessDeniedHandler securityAccessDeniedHandler;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    /**
     * BCryptPasswordEncoder bean
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * JdbcTokenRepositoryImpl   token数据库生成
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        //设置数据源
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        //设置自动创建表，第一次开启，第二次记得注释掉
        //jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
        return jdbcTokenRepositoryImpl;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登录页面跳转/login.html,  /login对应表单的action地址，成功后跳转/toMain
        http.formLogin()
                //自定义用户名，密码，对应login.html的用户名，密码的name,以Post请求
//            .passwordParameter("pwd")
//            .usernameParameter("loginname")
            //.loginPage("/login.html")
            .loginPage("/toLogin")
            .loginProcessingUrl("/login")
            //.successForwardUrl("/toMain")
            //重写跳转成功失败页面，如跳转百度之类的要重写AuthenticationSuccessHandler,使用重定向
            .successHandler(new SecurityAuthenticationSuccessHandler("/main.html"))
            //.failureForwardUrl("/loginError");//登录失败页面
            .failureHandler(new SecurityAuthenticationFailureHandler("/loginError.html"));

        //过滤所有请求注册
        http.authorizeRequests()
            //放行登录页面,anyRequest().authenticated()有先后顺序，如果anyRequest().authenticated()在前，那么全部拦截，添加antMatchers无效，直接控制台报错
            //.antMatchers("/login.html").permitAll()
            .antMatchers("/toLogin").permitAll()
            .antMatchers("/loginError.html").permitAll()
            .antMatchers("/css/**","/images/**","/js/**").permitAll()
            //.antMatchers("/**/*.png").permitAll()//过滤项目下所有的png格式的文件
            //.regexMatchers(".+[.]png").permitAll()//匹配所有的png格式的文件
            //.regexMatchers(HttpMethod.POST, "/demo").permitAll()//匹配POST的请求
            //.mvcMatchers("/demo").servletPath("/liao").permitAll()
            //权限控制
            //.antMatchers("/admin.html").hasAuthority("admin")
            //权限控制，多个权限都可以访问
            //.antMatchers("/admin.html").hasAnyAuthority("admin", "superManager")
            //角色的话不需要再加ROLE_,hasRole会自动加ROLE_前缀
            //.antMatchers("/admin.html").hasRole("admin")
            //.antMatchers("/admin.html").hasAnyRole("admin", "superManager")
            //基于IP地址
            //.antMatchers("/admin.html").hasIpAddress("127.0.0.1")
            .anyRequest().authenticated();
            //自定义access方法
            //.anyRequest().access("@accessServiceImpl.hasPermission(request, authentication)");

        //自定义异常
        http.exceptionHandling().accessDeniedHandler(securityAccessDeniedHandler);

        //记住我
        http.rememberMe()
            //.rememberMeParameter()//默认是remember-me可以修改
            .userDetailsService(userDetailsService)//配置自定义的userDetailsService
            .tokenRepository(persistentTokenRepository)//配置token的存放地址
            .tokenValiditySeconds(60); //token失效时间，默认2周，现在配置60秒

        //退出登录
        http.logout()
            //重写退出登录url,对应的页面的退出url也一一对应
            //.logoutUrl("/user/logout")
            //退出成功后的url
            .logoutSuccessUrl("/login");

        //跨站请求伪造关闭
        //http.csrf().disable();
    }


}

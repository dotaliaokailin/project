package com.liao.config;

import com.liao.handler.SecurityAccessDeniedHandler;
import com.liao.handler.SecurityAuthenticationSuccessHandler;
import com.liao.handler.SecurityAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityAccessDeniedHandler securityAccessDeniedHandler;

    /**
     * BCryptPasswordEncoder bean
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登录页面跳转/login.html,  /login对应表单的action地址，成功后跳转/toMain
        http.formLogin()
                //自定义用户名，密码，对应login.html的用户名，密码的name,以Post请求
//            .passwordParameter("pwd")
//            .usernameParameter("loginname")
            .loginPage("/login.html")
            .loginProcessingUrl("/login")
            //.successForwardUrl("/toMain")
            //重写跳转成功失败页面，如跳转百度之类的要重写AuthenticationSuccessHandler,使用重定向
            .successHandler(new SecurityAuthenticationSuccessHandler("/main.html"))
            //.failureForwardUrl("/loginError");//登录失败页面
            .failureHandler(new SecurityAuthenticationFailureHandler("/loginError.html"));

        //过滤所有请求注册
        http.authorizeRequests()
            //放行登录页面,anyRequest().authenticated()有先后顺序，如果anyRequest().authenticated()在前，那么全部拦截，添加antMatchers无效，直接控制台报错
            .antMatchers("/login.html").permitAll()
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
            //.antMatchers("/admin.html").hasRole("adminRole")
            //.antMatchers("/admin.html").hasAnyRole("adminRole", "superManagerRole")
            //基于IP地址
            //.antMatchers("/admin.html").hasIpAddress("127.0.0.1")
            .anyRequest().authenticated();
            //自定义access方法
            //.anyRequest().access("@accessServiceImpl.hasPermission(request, authentication)");

        //自定义异常
        http.exceptionHandling().accessDeniedHandler(securityAccessDeniedHandler);

        //跨域防火墙关闭
        http.csrf().disable();
    }
}

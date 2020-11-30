package com.liao.auth;

import com.liao.system.service.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity //开启Spring Security
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解 如：@PreAuthorize() 注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
    * 重写的认证流程
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
       //使用 hash 的加密方式  加密 + 加盐
       return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //改用自己重写的userDetailsService
        //speing security明确规定数据库中密码必须加密
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //设置哪些文件夹不被拦截，一般对静态文件放行
        web.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/swagger-ui.html",
                "/v2/api-docs", // swagger api json
                "/swagger-resources/configuration/ui", // 用来获取支持的动作
                "/swagger-resources", // 用来获取api-docs的URI
                "/swagger-resources/configuration/security", // 安全选项
                "/swagger-resources/**",
                "/webjars/**",
                "/doc.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors().and().csrf().disable()
                // 解决不允许显示在iframe的问题
                .headers().frameOptions().disable().httpStrictTransportSecurity().disable()
                .and()
                .authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // web jars
                .antMatchers("/webjars/**").permitAll()
                // 查看SQL监控（druid）
                .antMatchers("/druid/**").permitAll()
                // 首页和登录页面
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                // swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                // 验证码
                .antMatchers("/captcha.jpg**").permitAll()
                // 服务监控
                .antMatchers("/actuator/**").permitAll()
                //验证码
                .antMatchers("/defaultKaptcha").permitAll()
                .antMatchers("/checkCode").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().addLogoutHandler(new MyLogoutHandler());
                ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}

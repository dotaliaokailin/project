//package com.liao.auth;
//
//import com.liao.system.service.auth.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    /**
//    * 重写的认证流程
//     */
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    /**
//    * 密码加密
//     */
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//       //使用 hash 的加密方式  加密 + 加盐
//       return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //改用自己重写的userDetailsService
//        //speing security明确规定数据库中密码必须加密
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //设置哪些文件夹不被拦截，一般对静态文件放行
//        web.ignoring().antMatchers("/css/**", "/js/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //设置http的认证方式
//        http.authorizeRequests()
//                //任何请求都要被认证
//                .anyRequest().authenticated()
//                .and()
//                //设置登录页面
//                .formLogin().loginPage("/login")
//                .defaultSuccessUrl("/").permitAll()
//                .and()
//                //设置登出页面
//                .logout().permitAll();
//
//        //跨域
//        http.csrf().disable();
//    }
//}

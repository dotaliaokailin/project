package com.liao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置类
 */
@Configuration
public class CrossOriginConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//所有mapping
                .allowedOrigins("*")//允许的特定来源
                //.allowedMethods("GET","POST","PUT","DELETE","OPTIONS","HEAD")//允许的方法
                .allowedMethods("*")
                .allowedHeaders("*")//允许的头部
                .allowCredentials(true)//是否允许跨域和cookie
                .maxAge(3600);//请求的响应秒数
    }
}

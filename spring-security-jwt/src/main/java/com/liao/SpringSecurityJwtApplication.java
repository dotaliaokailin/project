package com.liao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)//开启角色注解，才能在方法或者controller上使用@Secured注解
public class SpringSecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }

}

package com.liao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringSecurityJwtApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder pw = new BCryptPasswordEncoder();
        String encode = pw.encode("123456");
        System.out.println("encode:"+encode);
        System.out.println(pw.matches("123456", encode));
    }

}

package com.liao;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class JjwtDemoApplicationTests {

    @Test
    void contextLoads() {
        JwtBuilder jwtBuilder = Jwts.builder()
                //对应{ "id" : "666"}
                .setId("666")
                //对应{ "iat" : "时间格式的long"}
                .setIssuedAt(new Date())
                //对应{ "sub" : "Jack"}
                .setSubject("Jack")
                //盐值 
                .signWith(SignatureAlgorithm.HS256, "salt")
                //设置失效时间
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 1000))
                //自定义申明
                .claim("name", "凯林")
                .claim("sex", "boy")
                //.setClaims(map);
                ;

        //生成token
        String token = jwtBuilder.compact();
        System.out.println(token);

        String[] split = token.split("\\.");
        System.out.println(split.length);
        System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
        System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
    }

    @Test
    void parse(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJpYXQiOjE2MDQyMTQ4MDYsInN1YiI6IkphY2siLCJleHAiOjE2MDQyMTQ4NjYsIm5hbWUiOiLlh6_mnpciLCJzZXgiOiJib3kifQ.tv7IXe3pFjxuvaUlXsWETPgvX88hJ-6TDVHwXG2KeSM";
        Claims claims = (Claims) Jwts.parser()
                .setSigningKey("salt")
                .parse(token)
                .getBody();

        System.out.println(claims);
        System.out.println(claims.getSubject());
        System.out.println(claims.getId());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.get("name"));
        System.out.println(claims.get("sex"));
    }
}

package com.liao.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTTokenUtil {
    public static final String SECRET = "spring security Jwt Secret";
    public static final String BEARER = "Bearer:";
    public static final String AUTHORIZATION = "Authorization";

    public static String getToken(JSONObject user) {
        return Jwts.builder()
                .setSubject(JSONObject.toJSONString(user))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

}

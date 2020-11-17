package com.liao.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JWTTokenUtil {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "echisan";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 10L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    // 添加角色的key
    private static final String ROLE_CLAIMS = "rol";

    // 创建token的方法
    public static String createToken(String username, String role, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                // 这里要早set一点，放到后面会覆盖别的字段
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }



    // 从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    // 是否已过期
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    //获取jwt body
    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    //拿到用户角色
    public static String getUserRole(String token) {
        return (String)getTokenBody(token).get(ROLE_CLAIMS);
    }

    public static void main(String[] args) {
        Claims claims = JWTTokenUtil.getTokenBody("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaWFvIiwiaXNzIjoiZWNoaXNhbiIsImV4cCI6MTYwNTU5ODM2NSwiaWF0IjoxNjA1NTk0NzY1LCJyb2wiOlt7ImF1dGhvcml0eSI6IlJlZGlz55uR5o6n5ZGYIn0seyJhdXRob3JpdHkiOiLns7vnu5_nm5HmjqflkZgifSx7ImF1dGhvcml0eSI6Iui3keaJueS6uuWRmCJ9LHsiYXV0aG9yaXR5Ijoi5byA5Y-R5Lq65ZGYIn0seyJhdXRob3JpdHkiOiLmtYvor5XnlKjmiLcifSx7ImF1dGhvcml0eSI6InVzZXJzIn0seyJhdXRob3JpdHkiOiJ1c2VyOmFkZCJ9LHsiYXV0aG9yaXR5IjoidXNlcjpkZWxldGUifSx7ImF1dGhvcml0eSI6InVzZXI6ZWRpdCJ9LHsiYXV0aG9yaXR5IjoidXNlcjpzdGF0dXMifSx7ImF1dGhvcml0eSI6InVzZXI6dXBkYXRlIn0seyJhdXRob3JpdHkiOiJ1c2VyOmV4cG9ydCJ9LHsiYXV0aG9yaXR5IjoidXNlcjphc3NpZ24ifSx7ImF1dGhvcml0eSI6Im1lbnU6YWRkIn0seyJhdXRob3JpdHkiOiJtZW51OmVkaXQifSx7ImF1dGhvcml0eSI6Im1lbnU6ZXhwb3J0In0seyJhdXRob3JpdHkiOiJtZW51OnVwZGF0ZSJ9LHsiYXV0aG9yaXR5IjoibWVudTpkZWxldGUifSx7ImF1dGhvcml0eSI6InJvbGU6YXV0aG9yaXR5In0seyJhdXRob3JpdHkiOiJyb2xlOmFkZCJ9LHsiYXV0aG9yaXR5Ijoicm9sZTp1cGRhdGUifSx7ImF1dGhvcml0eSI6InJvbGU6c3RhdHVzIn0seyJhdXRob3JpdHkiOiJyb2xlOmVkaXQifSx7ImF1dGhvcml0eSI6InJvbGU6ZGVsZXRlIn0seyJhdXRob3JpdHkiOiJkZXBhcnRtZW50OmFkZCJ9LHsiYXV0aG9yaXR5IjoiZGVwYXJ0bWVudDplZGl0In0seyJhdXRob3JpdHkiOiJkZXBhcnRtZW50OnVwZGF0ZSJ9LHsiYXV0aG9yaXR5IjoiZGVwYXJ0bWVudDpkZWxldGUifSx7ImF1dGhvcml0eSI6ImVsLWljb24tZGF0ZSJ9LHsiYXV0aG9yaXR5IjoiZWwtaWNvbi1kYXRlIn0seyJhdXRob3JpdHkiOiJpblN0b2NrOmluIn0seyJhdXRob3JpdHkiOiJpblN0b2NrOnJlbW92ZSJ9LHsiYXV0aG9yaXR5IjoiaW5TdG9jazpwdWJsaXNoIn0seyJhdXRob3JpdHkiOiJpblN0b2NrOmRlbGV0ZSJ9LHsiYXV0aG9yaXR5IjoiaW5TdG9jazpiYWNrIn0seyJhdXRob3JpdHkiOiJpblN0b2NrOmRldGFpbCJ9LHsiYXV0aG9yaXR5IjoicHJvZHVjdDphZGQifSx7ImF1dGhvcml0eSI6InByb2R1Y3Q6ZWRpdCJ9LHsiYXV0aG9yaXR5IjoicHJvZHVjdDpkZWxldGUifSx7ImF1dGhvcml0eSI6InByb2R1Y3Q6cmVtb3ZlIn0seyJhdXRob3JpdHkiOiJwcm9kdWN0OnB1Ymxpc2gifSx7ImF1dGhvcml0eSI6InByb2R1Y3Q6YmFjayJ9LHsiYXV0aG9yaXR5IjoidXBsb2FkOmltYWdlIn0seyJhdXRob3JpdHkiOiJwcm9kdWN0OnVwZGF0ZSJ9LHsiYXV0aG9yaXR5IjoicHJvZHVjdENhdGVnb3J5OmFkZCJ9LHsiYXV0aG9yaXR5IjoicHJvZHVjdENhdGVnb3J5OmRlbGV0ZSJ9LHsiYXV0aG9yaXR5IjoicHJvZHVjdENhdGVnb3J5OnVwZGF0ZSJ9LHsiYXV0aG9yaXR5IjoicHJvZHVjdENhdGVnb3J5OmVkaXQifSx7ImF1dGhvcml0eSI6ImNvbnN1bWVyOmRlbGV0ZSJ9LHsiYXV0aG9yaXR5IjoiY29uc3VtZXI6ZWRpdCJ9LHsiYXV0aG9yaXR5IjoiY29uc3VtZXI6dXBkYXRlIn0seyJhdXRob3JpdHkiOiJjb25zdW1lcjphZGQifSx7ImF1dGhvcml0eSI6InN1cHBsaWVyOmRlbGV0ZSJ9LHsiYXV0aG9yaXR5Ijoic3VwcGxpZXI6dXBkYXRlIn0seyJhdXRob3JpdHkiOiJzdXBwbGllcjphZGQifSx7ImF1dGhvcml0eSI6InN1cHBsaWVyOmVkaXQifSx7ImF1dGhvcml0eSI6Im1hcDp2aWV3In0seyJhdXRob3JpdHkiOiJoZWFsdGg6cmVwb3J0In0seyJhdXRob3JpdHkiOiJsb2dpbjpsb2cifSx7ImF1dGhvcml0eSI6ImxvZ2luTG9nOmRlbGV0ZSJ9LHsiYXV0aG9yaXR5IjoibG9naW5Mb2c6YmF0Y2hEZWxldGUifSx7ImF1dGhvcml0eSI6ImxvZzpkZWxldGUifSx7ImF1dGhvcml0eSI6ImxvZzpiYXRjaERlbGV0ZSJ9LHsiYXV0aG9yaXR5Ijoid2VsY29tZTp2aWV3In0seyJhdXRob3JpdHkiOiLolKHlvpDlnaQifSx7ImF1dGhvcml0eSI6Ium5v-aZlyJ9LHsiYXV0aG9yaXR5Ijoi5LmU56Kn6JCdIn0seyJhdXRob3JpdHkiOiLnlKjmiLfmt7vliqDkurrlkZgifSx7ImF1dGhvcml0eSI6Iui_m-i0p-WRmCJ9LHsiYXV0aG9yaXR5IjoibGludXjov5Dnu7TkurrlkZgifSx7ImF1dGhvcml0eSI6Iua1i-ivleWRmOW3pSJ9LHsiYXV0aG9yaXR5Ijoi6L6F5a-85ZGY6ICB5biIIn0seyJhdXRob3JpdHkiOiLpg6jpl6jkuLvku7sifSx7ImF1dGhvcml0eSI6IueJqei1hOWFpeW6k-aTjeS9nOWRmCJ9LHsiYXV0aG9yaXR5Ijoi6LaF57qn566h55CG5ZGYIn1dfQ.UQkP6EUMiMg2uRqv-VeuRxOz_elE751EIvm4ZvEcmree3bbLO8C3-20JizA1xBTMLVa05tgv02nWGEsCpdBS7w");
        System.out.println(claims);
        System.out.println(claims.get(ROLE_CLAIMS));
    }
}

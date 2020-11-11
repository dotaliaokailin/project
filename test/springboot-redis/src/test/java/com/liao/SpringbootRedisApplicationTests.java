package com.liao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liao.pojo.User;
import com.liao.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SpringbootRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("name", "jack");
        System.out.println(redisTemplate.opsForValue().get("name"));
    }

    @Test
    void test() throws JsonProcessingException {
        User jack = new User("jack", 12);
        String user = new ObjectMapper().writeValueAsString(jack);
        //redisTemplate.opsForValue().set("user", user);
        redisTemplate.opsForValue().set("user", jack); //实体没有实现序列话会报错，JdkSerializationRedisSerializer
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

    @Test
    void test1(){
        redisUtil.set("name", "liao");
        System.out.println(redisUtil.get("name"));
    }
}

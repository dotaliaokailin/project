package com.liao.test;

import redis.clients.jedis.Jedis;

import java.util.HashMap;

/**
 * 使用场景：保存用户信息
 *
 */
public class HashTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll();

        System.out.println("往hash集合添加元素:" + jedis.hset("user:1", "name", "jack"));
        System.out.println("拿到hash集合的元素:" + jedis.hget("user:1", "name"));

        System.out.println("批量添加元素：" + jedis.hmset("user:1", new HashMap<String, String>(){
            {
                put("age", "12");
                put("sex", "boy");
            }
        }));

        System.out.println("批量获取元素：" + jedis.hmget("user:1", "name", "age", "sex"));

        System.out.println("获取集合的所有key-value:" + jedis.hgetAll("user:1"));

        System.out.println("获取集合的所有keys" + jedis.hkeys("user:1"));
        System.out.println("获取集合的所有values" + jedis.hvals("user:1"));

        System.out.println("删除集合中的某个key-value:" + jedis.hdel("user:1", "sex", "age"));
        System.out.println("集合的长度：" + jedis.hlen("user:1"));

        System.out.println("判断集合中是否存在key,存在返回1否则返回0:" + jedis.hexists("user:1", "name"));

        jedis.hset("user:1", "age", "12");
        System.out.println("自增N：" + jedis.hincrBy("user:1", "age", 2));
        System.out.println("自减N：" + jedis.hincrBy("user:1", "age", -2));

        System.out.println("如果存在，返回0，不创建.不存在则创建,返回1:" + jedis.hsetnx("user:1", "address", "guangzhou"));
        System.out.println("返回集合的所有key-value:" + jedis.hgetAll("user:1"));

        jedis.close();//关闭连接
    }
}

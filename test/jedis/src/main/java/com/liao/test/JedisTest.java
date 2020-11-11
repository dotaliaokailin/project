package com.liao.test;

import redis.clients.jedis.Jedis;

public class JedisTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println("连接状态："+jedis.ping());
        System.out.println("清空数据库:"+jedis.flushDB());
        System.out.println("切换数据库:"+jedis.select(1));
        System.out.println("设置name为jack:"+jedis.set("name", "jack"));
        System.out.println("设置password为123456:"+jedis.set("password", "123456"));
        System.out.println("判断某个键是否存在:"+jedis.exists("name"));
        System.out.println("name的数据类型:"+jedis.type("name"));
        System.out.println("password的数据类型:"+jedis.type("password"));
        System.out.println("随机获取一个key:"+jedis.randomKey());
        System.out.println("重命名name为username:"+jedis.rename("name", "username"));
        System.out.println("获取所有的key:"+jedis.keys("*"));
        System.out.println("删除name:"+jedis.del("name"));
        System.out.println("返回数据库的size:"+jedis.dbSize());
        System.out.println("清空所有的数据库数据:"+jedis.flushAll());

        jedis.close();//关闭连接
    }
}

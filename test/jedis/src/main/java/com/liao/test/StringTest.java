package com.liao.test;

import org.joda.time.DateTime;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * 使用场景：计数器，统计多单位的数量，粉丝数，对象缓存存储
 */
public class StringTest {
    public static void main(String[] args) {
        //连接redis
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println("是否连接成功:" + jedis.ping());
        System.out.println("清空数据库:" + jedis.flushDB());
        System.out.println("查询数据库大小:" + jedis.dbSize());
        System.out.println("查询所有的key:" + jedis.keys("*"));

        System.out.println("设置name的值为jack:" + jedis.set("name", "jack"));
        System.out.println("设置password的值为123456:" + jedis.set("password", "123456"));
        System.out.println("拿到name和password的值:" + jedis.get("name") + " : " + jedis.get("password"));
        System.out.println("设置值和过期时间：" + jedis.setex("time", 30, new DateTime().toString("yyyy-MM-dd HH:mm:ss")));
        System.out.println("如果key不存在则创建返回1，否则返回0：" + jedis.setnx("name", "tom"));

        System.out.println("批量创建：" + jedis.mset("key", "v", "key1", "v2"));
        System.out.println("批量获取：" + jedis.mget("key", "key1"));

        System.out.println("批量创建，不存在则创建，存在报错，全部创建不成功:" + jedis.msetnx("key", "abc", "key3", "v3"));
        System.out.println("先拿到key再设置value值：" + jedis.getSet("key", "hello"));

        System.out.println("查看key3是否存在:" + jedis.exists("key3"));

        System.out.println("移动当前key到数据库1中：" + jedis.move("key", 1));

        System.out.println("设置过期时间：" + jedis.expire("name", 10));
        System.out.println("拿到name的过期时间：" + jedis.ttl("name"));

        System.out.println("追加name的值加value，如果当前key不存在则新增name:" + jedis.append("name", " is a boy"));
        System.out.println("拿到name的值:" + jedis.get("name"));
        System.out.println("查看name的长度：" + jedis.strlen("name"));

        System.out.println("自增1：" + jedis.incr("password"));
        System.out.println("自减1：" + jedis.decr("password"));
        System.out.println("自增N：" + jedis.incrBy("password", 10));
        System.out.println("自减N：" + jedis.decrBy("password", 10));

        System.out.println("截取字符串name的start到end的位置，如果拿到全部字符串可以 0 -1,和 get name 一样:" + jedis.getrange("name", 1, 2));
        System.out.println("替换字符串name的end位置为value:" + jedis.setrange("name", 1, "oxo"));
        System.out.println("返回替换的字符串：" + jedis.getrange("name", 0 , -1));

        jedis.close();//关闭连接
    }
}

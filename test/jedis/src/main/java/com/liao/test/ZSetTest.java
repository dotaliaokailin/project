package com.liao.test;

import redis.clients.jedis.Jedis;

import java.util.HashMap;

/**
 * 使用场景：权重(普通消息：1 重要消息 2普通消息)， 排行榜应用实现取Top N实现
 */
public class ZSetTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll();

        System.out.println("添加集合：" + jedis.zadd("zSet", new HashMap<String, Double>(){
            {
                put("jack", 2500D);
                put("tom", 5000D);
                put("luck", 200D);
            }
        }));
        System.out.println("获取集合的所有元素:" + jedis.zrange("zSet", 0 , -1));
        System.out.println("根据分数升序排：" + jedis.zrangeByScoreWithScores("zSet", "-inf", "+inf"));

        jedis.zadd("scores", 100D, "a");
        jedis.zadd("scores", 90D, "b");
        jedis.zadd("scores", 75D, "c");
        jedis.zadd("scores", 60D, "d");

        System.out.println("根据分数降序排：" + jedis.zrevrangeByScoreWithScores("scores", 100, 0));
        System.out.println("删除指定的key:" + jedis.zrem("scores", "b"));
        System.out.println("获取集合的所有元素:" + jedis.zrange("scores", 0 , -1));
        System.out.println("返回集合的数量：" + jedis.zcard("scores"));
        System.out.println("查询总数-inf +inf:" + jedis.zcount("scores", "-inf", "+inf"));

        jedis.close();//关闭连接
    }
}

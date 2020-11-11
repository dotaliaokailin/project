package com.liao.test;

import redis.clients.jedis.Jedis;

/**
 * 使用场景：统计网址登陆人数
 */
public class HyperloglogTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll();

        System.out.println("插入数据user:" + jedis.pfadd("user", "jack", "tom", "jack", "jetty", "queen"));
        System.out.println("查询user登陆的人数：" + jedis.pfcount("user"));

        System.out.println("插入数据otherUser:" + jedis.pfadd("otherUser", "jack", "tom", "tom", "luck"));
        System.out.println("查询otherUser登陆的人数：" + jedis.pfcount("otherUser"));

        System.out.println("查询总登陆的人数：" + jedis.pfcount("user", "otherUser"));
        System.out.println("merge：" + jedis.pfmerge("user", "otherUser"));
        System.out.println("查询总登陆的人数：" + jedis.pfcount("user"));

        jedis.close();//关闭连接
    }
}

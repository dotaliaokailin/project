package com.liao.test;

import redis.clients.jedis.Jedis;

/**
 * 存储0和1，一般2个状态，适用场景：统计用户信息活跃和不活跃，登陆和未登陆，打卡等
 */
public class BigMapsTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll();
        //添加周一到周日打卡情况
        jedis.setbit("week", 0 , true);
        jedis.setbit("week", 1 , false);
        jedis.setbit("week", 2 , true);
        jedis.setbit("week", 3 , false);
        jedis.setbit("week", 4 , true);
        jedis.setbit("week", 5 , false);
        jedis.setbit("week", 6 , true);

        System.out.println("拿到周六打卡的情况：" + jedis.getbit("week", 5));
        System.out.println("拿到本周打卡的总数：" + jedis.bitcount("week"));

        jedis.close();//关闭连接
    }
}

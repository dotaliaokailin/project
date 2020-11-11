package com.liao.test;

import redis.clients.jedis.Jedis;

/**
 * 使用场景：随机数，共同好友，共同粉丝，二度好友（好友的好友）
 */
public class SetTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll();

        System.out.println("往set集合添加元素：" + jedis.sadd("set", "a", "a", "b", "c", "d"));
        System.out.println("遍历set集合中所有的元素：" + jedis.smembers("set"));
        System.out.println("查询set集合中是否存在a元素：" + jedis.sismember("set", "a"));

        System.out.println("随机获取指定集合的元素的值，不填则默认取一个：" + jedis.srandmember("set", 2));
        System.out.println("获取集合的长度：" + jedis.scard("set"));

        System.out.println("随机移除set集合中指定的个数的value:" + jedis.spop("set", 2));
        System.out.println("把集合set中的value剔除并移动到集合set1中:" + jedis.smove("set", "set1", "b"));

        jedis.sadd("mySet", "a", "b", "c", "d", "d");
        jedis.sadd("mySet1", "a", "a", "e", "f");
        System.out.println("返回集合mySet与mySet1中的set集合中的差集:" + jedis.sdiff("mySet", "mySet1"));
        System.out.println("返回集合mySet与mySet1中的set集合中的交集:" + jedis.sinter("mySet", "mySet1"));
        System.out.println("返回集合mySet与mySet1中的set集合中的并集:" + jedis.sunion("mySet", "mySet1"));

        jedis.close();//关闭连接
    }
}

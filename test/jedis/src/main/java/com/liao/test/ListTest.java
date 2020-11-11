package com.liao.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

/**
 * 使用场景：消息队列，栈
 */
public class ListTest {
    public static void main(String[] args) {
        //创建连接
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll();

        System.out.println("往list集合添加数据：" + jedis.lpush("list", "a", "b", "c", "d"));
        System.out.println("移除list集合左边一条数据：" + jedis.lpop("list"));
        System.out.println("移除list集合右边一条数据：" + jedis.rpop("list"));

        System.out.println("往list集合右边添加数据：" + jedis.rpush("list", "x"));
        System.out.println("遍历集合的所有元素：" + jedis.lrange("list", 0, -1));

        System.out.println("获取下标值：" + jedis.lindex("list", 0));
        System.out.println("设置集合list下标的值，如果下标不存在，报错。如果集合不存在也报错:" + jedis.lset("list", 1 , "123"));
        System.out.println("遍历集合的所有元素：" + jedis.lrange("list", 0, -1));

        System.out.println("获取集合的长度：" + jedis.llen("list"));

        System.out.println("删除集合list的几个value:" + jedis.lrem("list", 2, "c"));
        System.out.println("遍历集合的所有元素：" + jedis.lrange("list", 0, -1));

        System.out.println("截取集合list，开始位置到结束位置:" + jedis.ltrim("list", 0, -1));
        System.out.println("遍历集合的所有元素：" + jedis.lrange("list", 0, -1));

        System.out.println("移除list集合右边一个元素到otherList集合中:" + jedis.rpoplpush("list", "otherList"));
        System.out.println("判断集合otherList是否存在：" + jedis.exists("otherList"));

        System.out.println("往集合otherList元素x之前添加数据：" + jedis.linsert("otherList", ListPosition.BEFORE, "x", "hello"));
        System.out.println("往集合otherList元素x之后添加数据：" + jedis.linsert("otherList", ListPosition.AFTER, "x", "world"));
        System.out.println("遍历集合的所有元素：" + jedis.lrange("otherList", 0, -1));

        jedis.close();//关闭连接
    }
}

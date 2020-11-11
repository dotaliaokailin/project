package com.liao.test;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashMap;

/**
 * 事务
 */
public class TransactionTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll();
        JSONObject jack = new JSONObject();
        jack.putAll(new HashMap<String,Object>(){
            {
                put("name", "jack");
                put("age", 12);
            }
        });
        JSONObject tom = new JSONObject();
        tom.putAll(new HashMap<String,Object>(){
            {

                   put("name", "tom");
                put("age", 14);
            }
        });
        //jedis.watch();//开启监视
        Transaction multi = jedis.multi();//开启事务
        try {
            multi.set("jack", jack.toJSONString());
            multi.set("tom", tom.toJSONString());
            //设置异常
            int i = 1 / 0;
            multi.exec();//执行
        }catch (Exception e){
            multi.discard();//回滚
            e.printStackTrace();
        }finally {
            System.out.println("jack:" + jedis.get("jack"));
            System.out.println("tom:" + jedis.get("tom"));
            jedis.close();//关闭连接
        }
    }
}

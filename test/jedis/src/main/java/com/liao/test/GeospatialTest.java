package com.liao.test;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.GeoRadiusParam;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 地理位置
 */
public class GeospatialTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushAll();

        Long geoadd = jedis.geoadd("china:city", new HashMap<String, GeoCoordinate>() {
            {
                put("beijing", new GeoCoordinate(116.397128, 39.916527));
                put("guangzhou", new GeoCoordinate(113.27324, 23.15792));
                put("chengdu", new GeoCoordinate(104.10194, 30.65984));
                put("chongqing", new GeoCoordinate(106.54041, 29.40268));
                put("shanghai", new GeoCoordinate(121.48941, 31.40527));
                put("xian", new GeoCoordinate(108.93425, 34.23053));
            }
        });
        System.out.println("初始化城市：" + geoadd + "个");

        System.out.println("北京距离上海的直线距离：" + jedis.geodist("china:city", "beijing", "shanghai", GeoUnit.KM) + " 千米");

        System.out.println("查询北京上海的经纬度" + jedis.geopos("china:city", "shanghai", "beijing"));

        //查询经度纬度100 30 附近 2000KM距离的城市
        List<GeoRadiusResponse> georadius = jedis.georadius("china:city", 100, 30, 2000,
                GeoUnit.KM, GeoRadiusParam.geoRadiusParam()
                        .count(2)
                        .sortAscending()
                        .withCoord()
                        .withDist()
                        .withHash());

        georadius.forEach(city ->{
            //经度纬度  --- 相差的距离 --- 名字的长度 --- 原始分数
            System.out.println(city.getCoordinate()+ "---" + city.getDistance() + "---" + city.getMember().length + "---" + city.getRawScore());
        });
        System.out.println("-------------------------------------");
        //查询北京范围内1000KM距离的城市
        List<GeoRadiusResponse> georadiusByMember = jedis.georadiusByMember("china:city", "beijing", 2000, GeoUnit.KM,  GeoRadiusParam.geoRadiusParam()
                //.count(2)
                .sortAscending()
                .withCoord()
                .withDist()
                .withHash());
        georadiusByMember.forEach(city -> {
            //经度纬度  --- 相差的距离 --- 名字的长度 --- 原始分数
            System.out.println(city.getCoordinate()+ "---" + city.getDistance() + "---" + city.getMember().length + "---" + city.getRawScore());
        });

        //返回所有的城市
        Set<String> zrange = jedis.zrange("china:city", 0, -1);
        zrange.forEach( l -> {
            System.out.println(l);
        });

        //删除某个城市
        System.out.println("删除西安城市:" + jedis.zrem("china:city", "xian"));

        jedis.close();//关闭连接
    }
}

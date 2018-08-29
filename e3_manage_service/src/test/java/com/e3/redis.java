package com.e3;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class redis {
    @Test
    //获得单一的jedis对象操作数据库
    public void test1(){

        //1、获得连接对象
        Jedis jedis = new Jedis("132.232.17.190", 6379);
        jedis.auth("841303829");

        //2、获得数据
        String username = jedis.get("lw");
        System.out.println(username);

        //3、存储
        jedis.set("addr", "北京");
        System.out.println(jedis.get("addr"));


    }
}

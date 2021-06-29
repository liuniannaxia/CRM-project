package com.bjpowernode.crm.test;

import cn.hutool.core.bean.BeanUtil;
import com.bjpowernode.crm.base.util.RedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class TestRedis {
    @Test
    public void test01(){

        Jedis jedis = RedisUtil.getJedis();
        System.out.println(jedis);
    }


    @Test
    public void test02(){

        Jedis jedis = RedisUtil.getJedis();
        jedis.select(1);
        Map map = new HashMap();
        map.put("id","1");
        map.put("name","zhangsan");
        map.put("age","20");
        jedis.hmset("user:"+jedis.incr("index"),map);
        //对象转map，调用hutu工具类
        Map<String, Object> map1 = BeanUtil.beanToMap(map);
        //object类型转String类型
        for (Map.Entry<String,Object> entry:map1.entrySet()){
            map.put(entry.getKey(),entry.getValue()+"");

        }
    }

    @Test
    public void test03(){


    }
}

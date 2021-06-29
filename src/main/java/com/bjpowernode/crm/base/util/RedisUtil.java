package com.bjpowernode.crm.base.util;

import cn.hutool.core.bean.BeanUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * Company :  北京动力节点
 * Author :   Andy
 * Date : 2020/4/1
 * Description :
 */

public class RedisUtil {

    public static Jedis getJedis(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(500);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(100);
        JedisPool jedisPool =
                new JedisPool(config,"192.168.239.128",6379,1000,"123");
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    //把数据库查出来的数据放到redis中
    //泛型方法
    public static <T> void insertIntoRedis(List<T> t,int index,String keyPattern){
        Jedis jedis = getJedis();
        jedis.select(index);
        //有可能传过来的对象是一个集合，所以要遍历
        for (T t1:t){
            Map data = new HashMap();
            Map<String, Object> map = BeanUtil.beanToMap(t1);
            //遍历map 把object转换成String类型
            for(Map.Entry<String,Object> entry : map.entrySet()){
                data.put(entry.getKey(),entry.getValue()+"");
            }
            //注意：下面的index不是上面传的index，incr可以创建没有的值，默认从0开始递增，所以第一个是1
            jedis.hmset(keyPattern+":"+jedis.incr("index"),data);
        }
    }

    //把redis查出来的数据放到对象中
    public static <T> List<T> redisToBean(Class<T> tClass,int index,String keyPattern){
        T t = null;
        List<T> list = new ArrayList<>();
        try {

            Jedis jedis =getJedis();
            jedis.select(index);
            //这个keyPattern是用户输入的带通配符的字符串
            Set<String> keys = jedis.keys(keyPattern);
            if (keys.size()>0){
                for (String key : keys) {
                    //通过用户传入的.class创建对象（放在循环里面每次创建一个新的对象）
                    t = tClass.newInstance();
                    Map<String, String> map = jedis.hgetAll(key);
                    BeanUtil.fillBeanWithMap(map, t, true);
                    list.add(t);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
}

package com.blp.sentenceLib.util;

/**
 * @author Summerday
 */

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.Set;

/**
 * Jedis工具类
 */
public final class JedisUtil {

    private static JedisPool jedisPool;

    static {
        // 池化配置，属性就不细说了
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMaxTotal(10);
        // 最长等待时间
        config.setMaxWaitMillis(2000L);
        // 实例化一个连接池
        jedisPool = new JedisPool(config, "192.168.88.63", 6379, 2000);

    }

    /**
     * 获取jedis连接
     * 用完需要手动释放 #close()
     */
    public static Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
//        jedis.select(4);
        return jedis;
    }

    /**
     * 关闭Jedis
     */
    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 清空重置Jedis
     */
    private static void reset(Jedis jedis) {
        if (jedis != null) {
            Set<String> keys = jedis.keys("*");
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                jedis.del(next);
            }
        }
    }

    /**
     * 查看Jedis
     */
    private static void find(Jedis jedis) {
        if (jedis != null) {
            Set<String> keys = jedis.keys("*");
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                System.out.println(next);
            }
        }
    }

    public static void main(String[] args) {
        Jedis jedis = getJedis();
        // Map<String, String> map = jedis.hgetAll("17");
        // for(Map.Entry entry : map.entrySet()){
        // System.out.print(entry.getKey() + "\t");
        // System.out.println(entry.getValue());
        // }
        // String test = jedis.get("测试");
        // List<String> jobIds = jedis.lrange("blp_interrupt_job_list:7600020601:麦克风输入", 0, -1);
        // for(String job : jobIds){
        // System.out.print(job + "\t");
        // }
        // System.out.println(test);
        // jedis.close();

    }
}

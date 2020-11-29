package com.tl.eccommerceorderservice.lock.lock.sub;


import redis.clients.jedis.Jedis;

/**
 * 获取分布式锁失败，订阅锁释放消息，继而再次尝试获取锁
 *      一般获取锁失败，直接返回失败给客户端 ,但订阅锁释放消息容易造成大量用户线程阻塞
 * @author lintan 2020/11/22
 */
public class RedisLockSubscribe {

    //频道
    private static final String channel ="REDIS_DISTRIBUTE_LOCK_WAITING";

    private static void subscribeLockRelease(Jedis jedis ){

    }
}

package com.tl.eccommerceorderservice.lock.lock;


import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * 基于setnx+expire 实现分布式锁
 *      加锁：set key value px milliseconds nx
 *              PX milliseconds – 设置键key的过期时间，单位时毫秒
 *              NX – 只有键key不存在的时候才会设置key的值
 *      解锁：redis + lua脚本
 *
 *      实现原则：
 *           setnx +expire非原子性，故需要使用set key value px milliseconds nx 或者使用lua脚本
 *          加锁与解锁的必须为同一个客户端
 *          超时可能导致并发
 *
 * @author lintan 2020/11/22
 */
public class RedisDistributeLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    private static final Long RELEASE_SUCCESS = 1L;


    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}

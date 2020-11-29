package com.tl.eccommerceorderservice.lock.redission;

import com.tl.eccommerceorderservice.lock.RedissonManager;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class RedissonLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonLock.class);

    final ThreadLocal<RedissonManager> redissonManager = new ThreadLocal<RedissonManager>();

    public RedissonLock(RedissonManager redissonManager) {
        this.redissonManager.set(redissonManager);
    }

    public RedissonLock() {}

    /**
     * 加锁操作
     * @return
     */
    public boolean lock(String lockName, long expireSeconds) {
        RLock rLock = redissonManager.get().getRedisson().getLock(lockName);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock(0, expireSeconds, TimeUnit.SECONDS);
            if (getLock) {
                LOGGER.info("获取Redisson分布式锁[成功],lockName={}", lockName);
            } else {
                LOGGER.info("获取Redisson分布式锁[失败],lockName={}", lockName);
            }
        } catch (InterruptedException e) {
            LOGGER.error("获取Redisson分布式锁[异常]，lockName=" + lockName, e);
            e.printStackTrace();
            return false;
        }
        return getLock;
    }

    /**
     * 解锁
     * @param lockName
     */
    public void release(String lockName) {
        redissonManager.get().getRedisson().getLock(lockName).unlock();
    }

    public RedissonManager getRedissonManager() {
        return redissonManager.get();
    }

    public void setRedissonManager(RedissonManager redissonManager) {
        this.redissonManager.set(redissonManager);
    }

}

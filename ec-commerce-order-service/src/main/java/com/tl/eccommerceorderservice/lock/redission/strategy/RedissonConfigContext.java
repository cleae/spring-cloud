package com.tl.eccommerceorderservice.lock.redission.strategy;


import com.tl.eccommerceorderservice.lock.redission.RedissonConfig;
import org.redisson.config.Config;

/**
 * 配置redisson 连接上下文，可以获取redis搭建模式
 */
public class RedissonConfigContext {

    private RedissonConfigStrategy redissonConfigStrategy;

    public RedissonConfigContext(RedissonConfigStrategy redisConfigStrategy) {
        this.redissonConfigStrategy = redisConfigStrategy;
    }

    /**
     * 配置上下文
     * @param redissonProperties
     * @return
     */
    public Config createRedissonConfig(RedissonConfig redissonProperties) {
        return this.redissonConfigStrategy.createRedissonConfig(redissonProperties);
    }
}

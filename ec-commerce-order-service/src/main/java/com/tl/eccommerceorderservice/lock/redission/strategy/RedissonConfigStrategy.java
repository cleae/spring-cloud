package com.tl.eccommerceorderservice.lock.redission.strategy;


import com.tl.eccommerceorderservice.lock.redission.RedissonConfig;
import org.redisson.config.Config;

/**
 * redis 配置策略
 *          单机/主从/主从+哨兵模式/集群
 * @author  lintan 2020/11/29
 */
public interface RedissonConfigStrategy {

    /**
     *
     * @param config 连接及其配置策略
     * @return
     */
    Config createRedissonConfig(RedissonConfig config);
}

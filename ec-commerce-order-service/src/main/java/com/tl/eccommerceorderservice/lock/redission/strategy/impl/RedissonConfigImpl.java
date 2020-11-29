package com.tl.eccommerceorderservice.lock.redission.strategy.impl;

import com.tl.eccommerceorderservice.lock.redission.RedissonConfig;
import com.tl.eccommerceorderservice.lock.redission.strategy.RedissonConfigStrategy;
import org.apache.commons.lang.StringUtils;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 单机模式配置
 */
public class RedissonConfigImpl implements RedissonConfigStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonConfig redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String redisAddr = address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSingleServer().setPassword(password);
            }
            LOGGER.info("初始化[standalone]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            LOGGER.error("standalone Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}

package com.tl.eccommerceorderservice.lock.redission.strategy.impl;

import com.tl.eccommerceorderservice.lock.redission.RedissonConfig;
import com.tl.eccommerceorderservice.lock.redission.strategy.RedissonConfigStrategy;
import org.apache.commons.lang.StringUtils;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 哨兵模式配置
 */
public class SentineRedissonConfigImpl implements RedissonConfigStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(SentineRedissonConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonConfig redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String sentinelAliasName = addrTokens[0];
            /**设置redis配置文件sentinel.conf配置的sentinel别名*/
            config.useSentinelServers()
                    .setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            /**设置sentinel节点的服务IP和端口*/
            for (int i = 1; i < addrTokens.length; i++) {
                config.useSentinelServers().addSentinelAddress(addrTokens[i]);
            }
            LOGGER.info("初始化[sentinel]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            LOGGER.error("sentinel Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}

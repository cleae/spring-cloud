package com.tl.eccommerceorderservice.lock.redission.strategy.impl;

import com.tl.eccommerceorderservice.lock.redission.RedissonConfig;
import com.tl.eccommerceorderservice.lock.redission.strategy.RedissonConfigStrategy;
import org.apache.commons.lang.StringUtils;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 集群模式配置
 */
public class ClusterRedissonConfigImpl implements RedissonConfigStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterRedissonConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonConfig redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            String[] addrTokens = address.split(",");
            /**设置cluster节点的服务IP和端口*/
            for (int i = 0; i < addrTokens.length; i++) {
                config.useClusterServers()
                        .addNodeAddress(addrTokens[i]);
                if (StringUtils.isNotBlank(password)) {
                    config.useClusterServers().setPassword(password);
                }
            }
            LOGGER.info("初始化[cluster]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            LOGGER.error("cluster Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}

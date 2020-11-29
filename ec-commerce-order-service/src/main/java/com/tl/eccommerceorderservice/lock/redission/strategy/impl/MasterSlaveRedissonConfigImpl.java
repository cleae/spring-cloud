package com.tl.eccommerceorderservice.lock.redission.strategy.impl;

import com.tl.eccommerceorderservice.lock.redission.RedissonConfig;
import com.tl.eccommerceorderservice.lock.redission.strategy.RedissonConfigStrategy;
import org.apache.commons.lang.StringUtils;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 主从配置
 */
public class MasterSlaveRedissonConfigImpl implements RedissonConfigStrategy {


    private static final Logger LOGGER = LoggerFactory.getLogger(MasterSlaveRedissonConfigImpl.class);

    @Override
    public Config createRedissonConfig(RedissonConfig redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String masterNodeAddr = addrTokens[0];
            /**设置主节点ip*/
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            if (StringUtils.isNotBlank(password)) {
                config.useMasterSlaveServers().setPassword(password);
            }
            config.useMasterSlaveServers().setDatabase(database);
            /**设置从节点，移除第一个节点，默认第一个为主节点*/
            List<String> slaveList = new ArrayList<>();
            slaveList.addAll(Arrays.asList(addrTokens));
            slaveList.remove(0);

            config.useMasterSlaveServers().addSlaveAddress((String[]) slaveList.toArray());
            LOGGER.info("初始化[MASTERSLAVE]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            LOGGER.error("MASTERSLAVE Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}

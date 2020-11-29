package com.tl.eccommerceorderservice.lock;

import com.google.common.base.Preconditions;
import com.tl.eccommerceorderservice.lock.redission.RedissonConfig;
import com.tl.eccommerceorderservice.lock.redission.strategy.RedissonConfigContext;
import com.tl.eccommerceorderservice.lock.redission.strategy.RedissonConfigStrategy;
import com.tl.eccommerceorderservice.lock.redission.strategy.constant.RedissonConnectionType;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 装配 Redisson redisson
 */
public class RedissonManager {

    private static final Logger logger = LoggerFactory.getLogger(Redisson.class);

    private Config config;

    private Redisson redisson;

    public RedissonManager(RedissonConfig redissonProperties) {
        try {
            config = createConfig(redissonProperties);
            redisson = (Redisson) Redisson.create(config);
        } catch (Exception e) {
            logger.error("Redisson init error", e);
            throw new IllegalArgumentException("please input correct configurations," +
                    "connectionType must in standalone/sentinel/cluster/masterslave");        }
    }

    public Redisson getRedisson() {
        return redisson;
    }

        /**
         * 初始化redisson连接上下文
         * @param redissonProperties
         * @return
         */
        @SuppressWarnings("all")
        Config createConfig(RedissonConfig redissonProperties) {
            String connectionType = redissonProperties.getType();
            RedissonConfigContext redissonConfigContext = null;
            RedissonConnectionType redissonConnectionType = RedissonConnectionType.getType(connectionType);
            if(null==redissonConnectionType)
                throw new IllegalArgumentException("redisson type error...");
            try {
                redissonConfigContext=new RedissonConfigContext((RedissonConfigStrategy) Class.forName(redissonConnectionType.getClassName()).newInstance());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("初始化连接策略失败 {}" ,e.getMessage());
            }
            return redissonConfigContext.createRedissonConfig(redissonProperties);//may produce nullpoint
        }


}



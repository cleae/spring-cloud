package com.tl.eccommerceorderservice.lock.redission;


import com.tl.eccommerceorderservice.lock.RedissonManager;
import org.redisson.Redisson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * redisson 装配
 * @author lintan 2020/11/29
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonConfig.class)//正式装配redisConfig
public class RedissonAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    @Bean
    @ConditionalOnBean
    @Order(value = 2)
    public RedissonLock redissonLock(RedissonManager redissonManager) {
        RedissonLock redissonLock = new RedissonLock();
        redissonLock.setRedissonManager(redissonManager);
        logger.info("[RedissonLock]组装完毕");
        return redissonLock;
    }

    @Bean
    @ConditionalOnBean
    @Order(value = 1)
    public RedissonManager redissonManager(RedissonConfig redissonProperties) {
        RedissonManager redissonManager =
                new RedissonManager(redissonProperties);
        logger.info("[RedissonManager]组装完毕,当前连接方式:" + redissonProperties.getType() +
            ",连接地址:" + redissonProperties.getAddress());
        return redissonManager;
    }
}


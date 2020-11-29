package com.tl.eccommerceorderservice.lock.config;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis 连接池
 */
@Component
public class JedisPoolInit {

    @Bean
    public JedisPool cresteJedisPool(JedisConfig config){

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(config.getMaxTotal());
        jedisPoolConfig.setMaxIdle(config.getMaxIdle());
        jedisPoolConfig.setMinIdle(config.getMinIdle());
        jedisPoolConfig.setTestOnBorrow(config.getTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(config.getTestOnReturn());
        /**连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true*/
        jedisPoolConfig.setBlockWhenExhausted(true);

        return new JedisPool(jedisPoolConfig, config.getRedisIp(), config.getRedisPort(), config.getRedisTimeout());
    }


}

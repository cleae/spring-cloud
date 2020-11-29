package com.tl.eccommerceorderservice.lock.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value="classpath:redis.properties")
public class JedisConfig {

    /**最大连接数*/
    @Value("${redis.server.maxTotal}")
    private  Integer maxTotal;

    /**最大连接数，在jedispool中最小的idle状态(空闲的)的jedis实例的个数*/
    @Value("${redis.server.minIdle}")
    private  Integer minIdle;

    /**在取连接空闲连接数，在jedispool中最大的idle状态(空闲的)的jedis实例的个数*/
    @Value("${redis.server.maxIdle}")
    private  Integer maxIdle;

    /**最小空闲时测试连接的可用性，在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。*/
    @Value("${redis.server.testOnBorrow}")
    private  Boolean testOnBorrow;

    /**再还连接时不测试连接的可用性，在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。*/
    @Value("${redis.server.testOnReturn}")
    private  Boolean testOnReturn;

    /**redis服务端ip*/
    @Value("${redis.server.redisIp}")
    private  String redisIp;

    /**redis服务端port*/
    @Value("${redis.server.redisPort}")
    private  Integer redisPort;

    /**redis连接超时时间*/
    @Value("${redis.server.redisTimeout}")
    private  Integer redisTimeout;


    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public String getRedisIp() {
        return redisIp;
    }

    public void setRedisIp(String redisIp) {
        this.redisIp = redisIp;
    }

    public Integer getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(Integer redisPort) {
        this.redisPort = redisPort;
    }

    public Integer getRedisTimeout() {
        return redisTimeout;
    }

    public void setRedisTimeout(Integer redisTimeout) {
        this.redisTimeout = redisTimeout;
    }
}

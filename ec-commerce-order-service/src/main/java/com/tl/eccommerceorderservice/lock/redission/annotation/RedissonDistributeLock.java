package com.tl.eccommerceorderservice.lock.redission.annotation;


import java.lang.annotation.*;

/**
 * 分布式锁注解
 * @author lintan 2020/11/29
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonDistributeLock {

    String name() default "REDIS_DISTRIBUTE_LOCK_KEY";

    long expire() default 3000L;//过期时间
}

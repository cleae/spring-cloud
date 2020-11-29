package com.tl.eccommerceorderservice.lock.redission.annotation;

import com.tl.eccommerceorderservice.lock.redission.RedissonLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 分布式锁切面类
 * @author  lintan 2020/11/29
 */
@Aspect
@Component
public class RedisDistributeLockAspectJ {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDistributeLockAspectJ.class);

    @Pointcut("@annotation(com.tl.eccommerceorderservice.lock.redission.annotation.RedissonDistributeLock)")
    public void distributedLock() {}

    @Autowired
    RedissonLock redissonLock;

    @Around("@annotation(distributedLock)")
    public void around(ProceedingJoinPoint joinPoint, RedissonDistributeLock distributedLock) {
        String lockName = distributedLock.name();
        long expireSeconds = distributedLock.expire();
        if (redissonLock.lock(lockName, expireSeconds)) {
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                redissonLock.release(lockName);
            }
        } else {
            LOGGER.error("获取Redis分布式锁[失败]");
        }
        LOGGER.info("[结束]执行RedisLock环绕通知");
    }
}

package com.tl.eccommerceorderservice.lock.lock;


import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * 当前线程获取锁之后，任务执行时间超时的情况，为获取锁的线程另开一个守护线程续命
 * @author lintan 2020/11/22
 */
@Component
public class RedisLockTimeout {
    private static final Logger logger=Logger.getLogger("RedisLockTimeout");


    /**
     * 为获取锁的线程增加守护线程，为将要过期但未释放的锁增加有效时间。
     * @param jedis
     * @param key 锁
     * @param milliseconds 续命的时间 毫秒
     * @return 守护线程
     */
    public  Thread cresteDemonThread(Jedis jedis ,String key , Long milliseconds){
        Thread demon=new Thread(()->{
            try{
                while(true){
                    TimeUnit.SECONDS.sleep(25);//获取所之后自动过期时间为30秒，守护线程先休眠25秒
                    String lock = jedis.get(key);
                    if(null==lock){
                        jedis.pexpire(key,milliseconds);
                    }
                }
            }catch (InterruptedException e){
                logger.info("获取锁的线程任务执行完毕...");
            }
        });
        demon.setDaemon(true);
        return demon;
    }

}

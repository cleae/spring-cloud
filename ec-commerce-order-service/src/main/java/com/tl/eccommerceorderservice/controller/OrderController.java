package com.tl.eccommerceorderservice.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tl.eccommerceorderservice.result.Resp;
import com.tl.eccommerceorderservice.service.ProductOrderService;
import com.tl.eccommerceorderservice.service.impl.ProductClientImpl;
import com.tl.eccommerceorderservice.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {


    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    ProductClientImpl productClient;

    @Autowired
    RedisService redisService;

    /**
     * 是否已经发送短信标识
     */
    private static final String COMMODITY_ERR="ec-commodity-advice";

    @RequestMapping("/save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Resp save(@RequestParam("user_id")int userId, @RequestParam("product_id") int productId){
        return Resp.ok(productClient.save(userId, productId)).code(200).msg("抢购成功..");//Response body was not logged
    }


    /**
     * 测试hystrix 熔断机制
     * @return
     */
    public Resp saveOrderFail(int user_id, int product_id){
        /**
         * 模拟调用远程接口出现问题，短信通知相应得负责人员
         */
        System.out.println("key=====>"+redisService.get(COMMODITY_ERR));
        Boolean exist = redisService.setNX(COMMODITY_ERR, 1);
        if(!exist){
            System.out.println("已发送报警短信。。。1"+exist);
        }else {
            //异步发送短信
            int corePoolSize = 2;
            int maxPoolSize = 6;
            long keepAliveTime = 10;
            TimeUnit unit = TimeUnit.SECONDS;
            BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);
            ThreadFactory threadFactory = new NameTreadFactory();
            RejectedExecutionHandler handler = new MyIgnorePolicy();
            ThreadPoolExecutor executor = null;
            try {
                executor = new ThreadPoolExecutor(corePoolSize,
                        maxPoolSize, keepAliveTime, unit,
                        workQueue, threadFactory, handler);
                /* 预启动所有核心线程  提升效率 */
                executor.prestartAllCoreThreads();
                RunnableTask task = new RunnableTask("192.168.1.66 主机发送短信线程");
                executor.submit(task);
                System.out.println("发送短信任务提交完毕le。。");
            } finally {
                assert executor != null;
                executor.shutdown();
            }
        }
        return Resp.fail().code(-1).msg("活动实在是太火爆了，请稍等再来");
    }



    /**
     * 线程工厂
     */
    static class NameTreadFactory implements ThreadFactory {
        /* 线程id */
        private final AtomicInteger threadId = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread t = new Thread(runnable, "线程-" + threadId.getAndIncrement());
            System.out.println(t.getName() + " 已经被创建");
            return t;
        }
    }

    /**
     * 线程池拒绝策略
     */
    public static class MyIgnorePolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor e) {
            doLog(runnable, e);
        }

        private void doLog(Runnable runnable, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println(runnable.toString() + " rejected");
        }
    }

    /**
     * 线程
     */
    static class RunnableTask implements Runnable {
        private String name;

        public RunnableTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+":"+this.toString() + " 正在发送短信报警。。");
                //让任务执行慢点
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "RunnableTask [name=" + name + "]";
        }
    }
}

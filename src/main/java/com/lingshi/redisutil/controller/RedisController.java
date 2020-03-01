package com.lingshi.redisutil.controller;

import com.lingshi.redisutil.redission.DistributedLocker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisController
 * @Create By: chenxihua
 * @Author: Administrator
 * @Date: 2020/3/1 16:49
 **/
@RestController
@RequestMapping("/redisson")
public class RedisController {

    private final static Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private DistributedLocker distributedLocker;

    @GetMapping("/test")
    public void redissonTest() {
        String key = "redisson_key";
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        logger.info("=======线程开启=======", Thread.currentThread().getName());
                        /*
                         * distributedLocker.lock(key,10L); //直接加锁，获取不到锁则一直等待获取锁
                         * Thread.sleep(100); //获得锁之后可以进行相应的处理
                         * ("======获得锁后进行相应的操作======"+Thread.currentThread().getName());
                         * distributedLocker.unlock(key); //解锁
                         * System.err.println("======"+Thread.currentThread().getName());
                         */
                        // 尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
                        boolean isGetLock = distributedLocker.tryLock(key, TimeUnit.SECONDS, 5L, 10L);
                        if (isGetLock) {
                            logger.info("线程: {}", Thread.currentThread().getName() + ",获取到了锁");
                            Thread.sleep(500); // 获得锁之后可以进行相应的处理
                            logger.info("======获得锁后进行相应的操作===  {}", Thread.currentThread().getName());

                            /**  distributedLocker.unlock(key);
                             *   即使没有解锁，它都会自动解锁的了，因为在获取锁的地方已经设置了10秒后会自动解锁
                             *   但是设置这个，是如果业务完成了，就尽快解锁，好让下一个任务获取到锁
                              */
                            distributedLocker.unlock(key);
                            logger.warn("解锁完毕，线程释放： {}", Thread.currentThread().getName());
                        }else {
                            logger.warn("获取不到锁，放弃了。。。");
                        }
                    } catch (Exception e) {
                        logger.warn("出现过异常：{}, {}", e.getMessage(), e.getCause().toString());
                        distributedLocker.unlock(key);
                    }
                }
            });
            t.start();
        }
    }
}

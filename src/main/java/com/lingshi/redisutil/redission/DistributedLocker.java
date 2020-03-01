package com.lingshi.redisutil.redission;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: DistributedLocker
 * @Create By: chenxihua
 * @Author: Administrator
 * @Date: 2020/3/1 16:37
 **/
public interface DistributedLocker {


    RLock lock(String lockKey);

    RLock lock(String lockKey, long timeout);

    RLock lock(String lockKey, TimeUnit unit, long timeout);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);


}

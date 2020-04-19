package com.aias.learn.distributed.lock.redis;

import com.aias.learn.distributed.lock.Lock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/19
 */
public class RedisRedLock implements Lock {

    /**
     * redis 客户端
     */
    private RedissonClient redissonClient;

    /**
     * 分布式锁的键值
     */
    private String lockKey;

    private RLock redLock;
    /**
     * 锁的有效时间 10s
     */
    int expireTime = 10 * 1000;

    /**
     * 获取锁的超时时间
     */
    int acquireTimeout = 500;

    public RedisRedLock(RedissonClient redissonClient, String lockKey) {
        this.redissonClient = redissonClient;
        this.lockKey = lockKey;
    }

    @Override
    public boolean lock() {
        redLock = redissonClient.getLock(lockKey);
        boolean isLock;
        try {
            isLock = redLock.tryLock(acquireTimeout, expireTime, TimeUnit.MILLISECONDS);
            if (isLock) {
                System.out.println(Thread.currentThread().getName() + " " + lockKey + "获得了锁");
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unlock() {
        if (null == redLock) {
            return false;
        }
        redLock.unlock();
        return true;
    }
}

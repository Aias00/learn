package com.aias.learn.distributed.lock.redis;

import com.aias.learn.distributed.lock.Lock;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/19
 */
public class RedisSimpleLock implements Lock {

    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private String LOCK_VALUE;

    private Jedis jedis;
    /**
     * 锁的
     */
    private String lockKey;

    /**
     * 锁的超时时间 10s
     */
    int expireTime = 10 * 1000;

    /**
     * 锁等待，防止线程饥饿
     */
    int acquireTimeout = 1000;

    /**
     * 获取指定键值的锁
     *
     * @param jedis   jedis Redis客户端
     * @param lockKey 锁的键值
     */
    public RedisSimpleLock(Jedis jedis, String lockKey) {
        this.jedis = jedis;
        this.lockKey = lockKey;
    }

    /**
     * 获取指定键值的锁,同时设置获取锁超时时间
     *
     * @param jedis          jedis Redis客户端
     * @param lockKey        锁的键值
     * @param acquireTimeout 获取锁超时时间
     */
    public RedisSimpleLock(Jedis jedis, String lockKey, int acquireTimeout) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.acquireTimeout = acquireTimeout;
    }

    /**
     * 获取指定键值的锁,同时设置获取锁超时时间和锁过期时间
     *
     * @param jedis          jedis Redis客户端
     * @param lockKey        锁的键值
     * @param acquireTimeout 获取锁超时时间
     * @param expireTime     锁失效时间
     */
    public RedisSimpleLock(Jedis jedis, String lockKey, int acquireTimeout, int expireTime) {
        this.jedis = jedis;
        this.lockKey = lockKey;
        this.acquireTimeout = acquireTimeout;
        this.expireTime = expireTime;
    }

    @Override
    public boolean lock() {
        try {
            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            // uuid 当作 lockValue
            LOCK_VALUE = UUID.randomUUID().toString();
            // 在超时时间内 循环获取锁
            while (System.currentTimeMillis() < end) {
                String result = jedis.set(lockKey, LOCK_VALUE, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                if (LOCK_SUCCESS.equals(result)) {
                    return true;
                }
                try {
                    // 睡眠100毫秒之后重试
                    TimeUnit.MICROSECONDS.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean unlock() {
        if (StringUtils.isEmpty(LOCK_VALUE)) {
            return false;
        }
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return " +
                "0 end";
        Object result = null;
        try {
            result = jedis.eval(script, Collections.singletonList(lockKey),
                    Collections.singletonList(LOCK_VALUE));
            if (RELEASE_SUCCESS.equals(result)) {
                System.out.println("release lock success, requestToken:{}" + LOCK_VALUE);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        System.out.println("release lock failed, requestToken:{}, result:{}" + LOCK_VALUE + "->" + result);
        return false;
    }
}

package com.aias.learn.cache;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/25
 * 缓存接口
 */
public interface Cache {
    /**
     * 存入
     *
     * @param key  缓存Key
     * @param data 缓存对象
     * @return
     */
    <T> T set(String key, T data);

    /**
     * 存入 并设置存活多少单位的时间
     *
     * @param key
     * @param data
     * @param liveTime
     * @param timeUnit
     * @return
     */
    <T> T set(String key, T data, long liveTime, TimeUnit timeUnit);

    /**
     * 存入 并设置在某个时间点过期
     *
     * @param key
     * @param data
     * @param expireTimeStamp
     * @return
     */
    <T> T set(String key, T data, long expireTimeStamp);

    /**
     * 取出
     *
     * @param key
     * @param <T>
     * @return
     */
    <T> T get(String key);

    /**
     * 某个key对应的value是否存在
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 返回所有存储的key集合
     *
     * @return
     */
    Set<String> keys();

    /**
     * 删除某个key对应的value
     *
     * @param key
     */
    void delete(String key);

    /**
     * 删除所有数据
     */
    void flushAll();
}

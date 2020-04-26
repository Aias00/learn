package com.aias.learn.cache;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author liuhy
 * @since 2020/4/25
 */
public class LocalCacheImpl implements Cache, Runnable {
    private static volatile LocalCacheImpl INSTANCE = null;

    private static Map<String, CacheObject<?>> database;

    Executor executor;

    private LocalCacheImpl() {
        database = Maps.newConcurrentMap();
        executor = Executors.newSingleThreadExecutor();
        executor.execute(this);
    }

    /**
     * 保证单例
     *
     * @return
     */
    public static LocalCacheImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalCacheImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalCacheImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public <T> T set(String key, T data) {
        return set(key, data, 0L);
    }

    @Override
    public <T> T set(String key, T data, long liveTime, TimeUnit timeUnit) {
        long expireTimeStamp = System.currentTimeMillis();
        switch (timeUnit) {
            case SECONDS:
                expireTimeStamp += liveTime * 1000;
                break;
            case MINUTES:
                expireTimeStamp += liveTime * 1000 * 60;
                break;
            case HOURS:
                expireTimeStamp += liveTime * 1000 * 60 * 60;
                break;
            case DAYS:
                expireTimeStamp += liveTime * 1000 * 60 * 60 * 24;
                break;
            case MILLISECONDS:
                expireTimeStamp += liveTime;
                break;
            default:
                throw new UnsupportedOperationException("时间格式不支持");
        }
        return set(key, data, expireTimeStamp);
    }

    @Override
    public <T> T set(String key, T data, long expireTimeStamp) {

        CacheObject<T> cacheObject = new CacheObject<>();
        cacheObject.setData(data);
        cacheObject.setExpireTimeStamp(expireTimeStamp);
        database.put(key, cacheObject);

        return cacheObject.getData();
    }

    @Override
    public <T> T get(String key) {
        CacheObject<?> cacheObject = database.get(key);
        if (null == cacheObject) {
            return null;
        }
        if (!cacheObject.isValid()) {
            database.remove(key);
            return null;
        }

        return (T) cacheObject.getData();
    }

    @Override
    public boolean exists(String key) {
        CacheObject<?> cacheObject = database.get(key);
        if (null == cacheObject) {
            return false;
        }
        if (!cacheObject.isValid()) {
            database.remove(key);
            return false;
        }
        return true;
    }

    @Override
    public Set<String> keys() {
        return database.keySet().stream().filter(key -> database.get(key).isValid()).collect(Collectors.toSet());
    }

    @Override
    public void delete(String key) {
        database.remove(key);
    }

    @Override
    public void flushAll() {
        database.clear();
    }

    @Override
    public void run() {
        System.out.println("检查无效key 线程启动");
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (String key : database.keySet()) {
            if (database.get(key).isValid()) {
                continue;
            }
            database.remove(key);
        }

    }
}

package com.aias.learn.cache;

import java.io.Serializable;

/**
 * @author liuhy
 * @since 2020/4/25
 * 缓存对象
 */
public class CacheObject<T> implements Serializable {
    private static final long serialVersionUID = -6661390704607039085L;
    /**
     * 存储的数据对象
     */
    private T data;
    /**
     * 过期时间戳
     */
    private long expireTimeStamp;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getExpireTimeStamp() {
        return expireTimeStamp;
    }

    public void setExpireTimeStamp(long expireTimeStamp) {
        this.expireTimeStamp = expireTimeStamp;
    }

    /**
     * 是否过期/是否有效
     * 设置过期时间为0L 则对象一直有效
     * 当前时间小于过期时间 视为有效
     *
     * @return
     */
    public boolean isValid() {
        if (0L == expireTimeStamp) {
            return true;
        }
        long now = System.currentTimeMillis();
        return now < expireTimeStamp;
    }

}

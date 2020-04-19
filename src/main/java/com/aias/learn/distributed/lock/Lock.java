package com.aias.learn.distributed.lock;

/**
 * @author liuhy
 * @since 2020/4/17
 * 分布式锁接口
 */
public interface Lock {

    public boolean lock();

    public boolean unlock();

}

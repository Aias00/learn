package com.aias.learn.distributed.lock.zookeeper.zkclient;

import com.aias.learn.distributed.lock.Lock;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public abstract class ZookeeperAbstractLock implements Lock {

    private static final String CONNECTION_STRING = "localhost:2181";

    protected ZkClient zkClient = new ZkClient(CONNECTION_STRING);

    protected static final String PATH = "/lock";
    protected static final String PATH2 = "/lock2";

    @Override
    public boolean lock() {
        if (tryLock()) {
            System.out.println("获取到锁");
            return true;
        } else {
            // 等待
            waitLock();
            return lock();
        }
    }

    abstract void waitLock();

    abstract boolean tryLock();

}

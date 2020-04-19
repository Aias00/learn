package com.aias.learn.distributed.lock.zookeeper;

import com.aias.learn.distributed.lock.Lock;
import org.I0Itec.zkclient.ZkClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public abstract class ZookeeperAbstractLock implements Lock {

    private static final String CONNECTION_STRING = "localhost:2181";
    /**
     * zkClient
     */
    protected ZkClient zkClient = new ZkClient(CONNECTION_STRING);
    /**
     * curator
     */
    protected CuratorFramework curatorClient = CuratorFrameworkFactory.builder()
                                                               .connectString(CONNECTION_STRING)
                                                               // 超时时间
                                                               .sessionTimeoutMs(5000)
                                                               // 重试策略
                                                               .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                                               .build();

    protected static final String PATH = "/lock";

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

    abstract public void waitLock();

    abstract public boolean tryLock();

}

package com.aias.learn.distributed.lock.zookeeper.curator;

import com.aias.learn.distributed.lock.zookeeper.ZookeeperAbstractLock;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/19
 */
public class CuratorLock extends ZookeeperAbstractLock {

    // 重试策略，初始化每次重试之间需要等待的时间，基准等待时间为1秒, 最大重试次数为 3
//    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    // 创建共享锁
    static InterProcessLock lock = null;

    @Override
    public void waitLock() {

    }

    @Override
    public boolean tryLock() {
        // 启动客户端
        curatorClient.start();
        /**
         * InterProcessMutex：分布式可重入排它锁
         * InterProcessSemaphoreMutex：分布式排它锁
         * InterProcessReadWriteLock：分布式读写锁
         * InterProcessMultiLock：将多个锁作为单个实体管理的容器
         **/
        lock = new InterProcessSemaphoreMutex(curatorClient, PATH);

        try {
            // 阻塞等待获取锁对象成功
//            lock.acquire();
//            return true;

            // 或者 设置超时时间等待获取锁对象
            if (lock.acquire(60, TimeUnit.MINUTES)) {
                Stat stat = curatorClient.checkExists().forPath(PATH);
                if (null != stat) {
                    System.out.println("获取锁成功");
                    return true;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public void unlock() {
        try {
            lock.release();
            System.out.println("释放锁");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

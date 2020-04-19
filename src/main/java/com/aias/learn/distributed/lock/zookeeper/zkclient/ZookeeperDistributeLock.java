package com.aias.learn.distributed.lock.zookeeper.zkclient;

import com.aias.learn.distributed.lock.zookeeper.ZookeeperAbstractLock;
import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author liuhy
 * @since 2020/4/17
 * 简单临时节点 释放之后会有问题
 */
public class ZookeeperDistributeLock extends ZookeeperAbstractLock {

    private CountDownLatch countDownLatch = null;

    @Override
    public void waitLock() {
        System.out.println("获取锁资源失败,开始等待");
        // 注册一个监听
        IZkDataListener zkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // 唤醒等待的线程
                if (null != countDownLatch) {
                    countDownLatch.countDown();
                }
            }
        };
        // 注册事件监听
        zkClient.subscribeDataChanges(PATH, zkDataListener);
        try {
            // 如果路径已经存在
            if (zkClient.exists(PATH)) {
                countDownLatch = new CountDownLatch(1);
                // 等待线程被唤醒 取消此监听
                countDownLatch.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        zkClient.unsubscribeDataChanges(PATH, zkDataListener);
    }

    @Override
    public synchronized boolean tryLock() {
        try {
            // 尝试创建一个临时节点
            zkClient.createEphemeral(PATH);
            // 如果创建成功  返回true
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean unlock() {
        if (null == zkClient) {
            return false;
        }
        // 删除路径
        zkClient.delete(PATH);
        // 释放资源
        zkClient.close();
        System.out.println("释放锁资源...");
        return true;
    }
}

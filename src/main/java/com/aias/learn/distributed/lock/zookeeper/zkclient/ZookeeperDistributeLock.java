package com.aias.learn.distributed.lock.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class ZookeeperDistributeLock extends ZookeeperAbstractLock {

    private CountDownLatch countDownLatch = null;

    @Override
    void waitLock() {
        System.out.println("获取锁资源失败,开始等待");
        //
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // 唤醒等待的线程
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }
        };
        // 注册事件
        zkClient.subscribeDataChanges(PATH, iZkDataListener);
        if (zkClient.exists(PATH)) {
            countDownLatch = new CountDownLatch(1);
        }

    }

    @Override
    boolean tryLock() {
        try {
            // 创建一个临时节点
            zkClient.createEphemeral(PATH);
            // 如果创建成功  返回true
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void unlock() {

    }
}

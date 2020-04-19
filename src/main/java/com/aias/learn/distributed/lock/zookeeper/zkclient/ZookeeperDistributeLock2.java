package com.aias.learn.distributed.lock.zookeeper.zkclient;

import com.aias.learn.distributed.lock.zookeeper.ZookeeperAbstractLock;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class ZookeeperDistributeLock2 extends ZookeeperAbstractLock {
    private String prevPath;
    private String currentPath;

    private CountDownLatch countDownLatch = null;

    public ZookeeperDistributeLock2() {
        if (!this.zkClient.exists(PATH)) {
            this.zkClient.createPersistent(PATH);
        }
    }

    @Override
    public void waitLock() {
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
        zkClient.subscribeDataChanges(prevPath, zkDataListener);
        try {
            // 如果路径已经存在
            if (zkClient.exists(prevPath)) {
                // 等待线程被唤醒 取消此监听
                countDownLatch = new CountDownLatch(1);
                countDownLatch.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        zkClient.unsubscribeDataChanges(prevPath, zkDataListener);
    }

    @Override
    public synchronized boolean tryLock() {
        // 如果 currentPath 为空则为第一次尝试加锁，第一次加锁赋值currentPath
        if (StringUtils.isBlank(currentPath)) {
            // 创建一个临时顺序节点
            currentPath = this.zkClient.createEphemeralSequential(PATH + '/', "lock");
        }
        // 获取所有临时节点并排序，临时节点的名称为自增长的字符串如：0000000400
        List<String> children = this.zkClient.getChildren(PATH);
        Collections.sort(children);
        // 如果当前节点在所有节点中排名第一则获取锁成功
        if (currentPath.equals(PATH + '/' + children.get(0))) {
            return true;
        } else {
            // 如果不是排名第一，则获取前面的节点名称，并赋值给beforePath
            int index = Collections.binarySearch(children, currentPath.substring(PATH.length() + 1));
            prevPath = PATH + '/' + children.get(index - 1);
        }

        return false;
    }

    @Override
    public void unlock() {
        if (null != this.zkClient) {
            // 删除当前临时节点
            this.zkClient.delete(currentPath);
        }
    }
}

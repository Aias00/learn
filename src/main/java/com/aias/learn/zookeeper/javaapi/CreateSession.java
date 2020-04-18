package com.aias.learn.zookeeper.javaapi;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author liuhy
 * @since 2020/4/18
 * java api 创建与服务器的连接
 */
public class CreateSession {
    /**
     * 多个地址逗号分隔
     */
    private final static String CONNECTION_STRING = "localhost:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTION_STRING, 5000, watchedEvent -> {
            // 如果当前的连接状态是连接成功的，通过计数器去控制
            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
                System.out.println(watchedEvent.getState());
            }
        });
        countDownLatch.await();
        System.out.println(zooKeeper.getState());
    }

}

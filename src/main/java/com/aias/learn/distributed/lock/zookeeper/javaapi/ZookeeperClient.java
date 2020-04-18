package com.aias.learn.distributed.lock.zookeeper.javaapi;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class ZookeeperClient {
    private static final String connection_string = "localhost:2181";
    public static int sessionTimeOut = 5000;

    public static ZooKeeper getInstance() throws IOException, InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(connection_string, sessionTimeOut, watchedEvent -> {
            if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        return zooKeeper;
    }

}

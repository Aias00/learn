package com.aias.learn.zookeeper.javaapi;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author liuhy
 * @since 2020/4/18
 * 自定义watchEvent
 */
public class JavaApi implements Watcher {

    protected final static String CONNECTION_STRING = "localhost:2181";
    protected static CountDownLatch countDownLatch = new CountDownLatch(1);
    protected static CountDownLatch countDownLatch2 = new CountDownLatch(1);
    protected static ZooKeeper zooKeeper;
    protected static Stat stat = new Stat();

    @Override
    public void process(WatchedEvent watchedEvent) {
        //如果当前的连接状态是连接成功的，那么通过计数器去控制
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                countDownLatch.countDown();
                countDownLatch2.countDown();
                System.out.println("watcher->" + watchedEvent.getState() + "-->" + watchedEvent.getType());
            } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                try {
                    System.out.println("watcher->数据变更触发路径：" + watchedEvent.getPath() + "->改变后的值：" +
                            new String(zooKeeper.getData(watchedEvent.getPath(), true, stat)));
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                //子节点的数据变化会触发
                try {
                    System.out.println("watcher->子节点数据变更路径：" + watchedEvent.getPath() + "->节点的值：" +
                            new String(zooKeeper.getData(watchedEvent.getPath(), true, stat)));
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (watchedEvent.getType() == Event.EventType.NodeCreated) {
                //创建子节点的时候会触发
                try {
                    System.out.println("watcher->节点创建路径：" + watchedEvent.getPath() + "->节点的值：" +
                            new String(zooKeeper.getData(watchedEvent.getPath(), true, stat)));
                } catch (KeeperException | InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                //子节点删除会触发
                System.out.println("watcher->节点删除路径：" + watchedEvent.getPath());
            }
            System.out.println(watchedEvent.getType());
        }

    }
}

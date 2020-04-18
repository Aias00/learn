package com.aias.learn.distributed.lock.zookeeper.javaapi;

import com.aias.learn.distributed.lock.Lock;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class JavaApiLock implements Lock {

    private static final String ROOT_LOCKS = "/LOCKS";

    private ZooKeeper zooKeeper;
    /**
     * 会话超时时间
     */
    private int sessionTimeOut = 5000;
    /**
     * 记录锁节点id
     */
    private String lockId;

    /**
     * 节点存储的数据
     */
    private final static byte[] data = {1, 2};

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public JavaApiLock() throws IOException, InterruptedException {
        this.zooKeeper = ZookeeperClient.getInstance();
        this.sessionTimeOut = ZookeeperClient.sessionTimeOut;
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                JavaApiLock lock = null;
                try {
                    lock = new JavaApiLock();
                    latch.countDown();
                    latch.await();
                    lock.lock();
                    Thread.sleep(random.nextInt(500));
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            }).start();
        }
    }

    /**
     * 获得锁的方法
     *
     * @return
     */
    @Override
    public synchronized boolean lock() {
        try {
            // 创建临时有序节点
            lockId = zooKeeper.create(ROOT_LOCKS , data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "成功创建了lock节点,节点Id:" + lockId + ",开始竞争锁");

            // 获取根节点下的所有节点
            List<String> childrenNodes = zooKeeper.getChildren(ROOT_LOCKS, true);

            // 从小到大排序
            SortedSet<String> sortedSet = new TreeSet<>();
            for (String childrenNode : childrenNodes) {
                sortedSet.add(ROOT_LOCKS + "/" + childrenNode);
            }

            // 拿到最小的节点
            String first = sortedSet.first();
            if (lockId.equals(first)) {
                // 表示当前节点就是最小节点
                System.out.println(Thread.currentThread().getName() + "-->成功获取到锁，lock节点为：" + lockId);
                return true;
            }
            // 拿到所有比当前节点小的节点集合
            SortedSet<String> lessThanLockId = sortedSet.headSet(lockId);
            if (!lessThanLockId.isEmpty()) {
                // 拿到比当前LockId这个节点更小的上一个节点
                String prevLockId = lessThanLockId.last();
                // 监控这个节点
                zooKeeper.exists(prevLockId, new LockWatcher(countDownLatch));
                // countDownLatch减为0 说明前面的这个节点被删除了 / 等待超时
                countDownLatch.await(sessionTimeOut, TimeUnit.MICROSECONDS);
                // 上面这段代码意味着如果节点被删除或者超时了
                System.out.println(Thread.currentThread().getName() + "成功获取锁：" + lockId);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void unlock() {
        System.out.println(Thread.currentThread().getName() + "开始释放锁：" + lockId);
        try {
            zooKeeper.delete(lockId, -1);
            System.out.println("节点：" + lockId + "被删除");
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}

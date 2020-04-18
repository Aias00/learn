package com.aias.learn.zookeeper.javaapi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class ApiOperation extends JavaApi{

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(CONNECTION_STRING, 5000, new ApiOperation());
        countDownLatch.await();
        System.out.println(zooKeeper.getState());
        // 创建一个临时节点
        String res = zooKeeper.create("/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        // 增加一个watch
        zooKeeper.getData("/node1", new ApiOperation(), stat);
        System.out.println("创建成功:" + res);
        // 修改数据
        zooKeeper.setData("/node1", "456".getBytes(), -1);

        zooKeeper.setData("/node1", "157".getBytes(), -1);

        // 节点删除
        zooKeeper.delete("/node1", -1);

        // 创建子节点
        String path = "/node2";
        zooKeeper.create(path, "node2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        TimeUnit.SECONDS.sleep(1);
        Stat stat1 = zooKeeper.exists(path + "/node1", true);
        if (null == stat1) {
            // 表示节点不存在
            zooKeeper.create(path + "/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            TimeUnit.SECONDS.sleep(1);
        }
        zooKeeper.setData(path + "/node1", "157".getBytes(), -1);
        TimeUnit.SECONDS.sleep(1);

        //获取指定节点下的子节点
        List<String> children = zooKeeper.getChildren(path, true);
        System.out.println(children);

    }
}

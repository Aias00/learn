package com.aias.learn.zookeeper.javaapi;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class AuthControl extends JavaApi{

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(CONNECTION_STRING, 5000, new AuthControl());
        countDownLatch.await();
        zooKeeper.addAuthInfo("digest", "root:root".getBytes());
        ACL acl = new ACL(ZooDefs.Perms.CREATE, new Id("digest","root:root"));
        List<ACL> acls = new ArrayList<>();
        acls.add(acl);
        zooKeeper.create("/auth", "123".getBytes(), acls, CreateMode.PERSISTENT);
//        zooKeeper.create("/auth/order1-1","123".getBytes(),acls, CreateMode.EPHEMERAL);
        ZooKeeper zooKeeper1 = new ZooKeeper(CONNECTION_STRING, 5000, new JavaApi());
        countDownLatch2.await();
        zooKeeper1.delete("/auth", -1);
        // ACL  (create/delete/admin/read/write)
        // 权限模式：IP/Digest(username:passwod)/world/super
    }

}

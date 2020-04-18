package com.aias.learn.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class Session {

    protected final static String connection_string = "localhost:2181";

    public static void main(String[] args) {
// 创建会话的两种方式
        // 正常
        CuratorFramework framework = CuratorFrameworkFactory.newClient(connection_string, 5000, 5000,
                new ExponentialBackoffRetry(1000, 3));
        framework.start();

        //fluent风格
        CuratorFramework framework2 = CuratorFrameworkFactory.builder()
                                                             .connectString(connection_string)
                                                             .sessionTimeoutMs(5000)
                                                             .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                                             .namespace("curator").build();
        framework2.start();

        System.out.println("success");
    }

}

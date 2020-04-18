package com.aias.learn.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class CuratorUtil {
    private static CuratorFramework framework;
    private final static String connection_string = "localhost:2181";

    public static CuratorFramework getInstance() {
        //fluent风格
        framework = CuratorFrameworkFactory.builder()
                                           .connectString(connection_string)
                                           .sessionTimeoutMs(5000)
                                           .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                                           .build();
        framework.start();
        System.out.println("----------连接成功----------");
        return framework;
    }
}

package com.aias.learn.zookeeper.zkclient;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class Session {
    protected final static String connection_string = "localhost:2181";
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(connection_string, 4000);
        System.out.println(zkClient + "-->success");

    }
}

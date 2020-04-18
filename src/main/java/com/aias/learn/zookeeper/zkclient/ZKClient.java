package com.aias.learn.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class ZKClient {
    protected final static String CONNECTION_STRING = "localhost:2181";

    private static ZkClient getInstance() {
        return new ZkClient(CONNECTION_STRING, 4000);
    }

    public static void main(String[] args) throws InterruptedException {
        ZkClient zkClient = getInstance();
        // zkClient提供了递归创建节点的功能
        zkClient.createPersistent("/nodeTest/nodeTest1/test1", true);
        zkClient.createPersistent("/nodeTest/nodeTest2/test2", true);
        System.out.println("创建完成");
        // 递归删除
//        zkClient.deleteRecursive("/nodeTest");
        List<String> list = zkClient.getChildren("/nodeTest");
        for (String s : list) {
            System.out.println(s);
        }
        // 监听
        zkClient.subscribeDataChanges("/nodeTest", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(dataPath + "-->" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });
        zkClient.writeData("/nodeTest", "value");
        TimeUnit.SECONDS.sleep(1);
        // 子节点监听
        zkClient.subscribeChildChanges("/nodeTest", (parentPath, currentChilds) -> {
            System.out.println(parentPath + "--->" + currentChilds);
        });
        zkClient.delete("/nodeTest/nodeTest2/test2");
        zkClient.delete("/nodeTest/nodeTest2");
    }

}

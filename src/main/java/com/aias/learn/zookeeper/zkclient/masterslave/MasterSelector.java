package com.aias.learn.zookeeper.zkclient.masterslave;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/18
 * 选主服务
 */
public class MasterSelector {
    protected final static String CONNECTION_STRING = "localhost:2181";

    private static ZkClient getInstance() {
        return new ZkClient(CONNECTION_STRING, 5000, 5000, new SerializableSerializer());
    }

    static Map<Integer, MasterSelector> masterSelectors = new HashMap<>();

    public static void main(String[] args) {

        try {
            for (int i = 0; i < 10; i++) {
                UserCenter userCenter = new UserCenter();
                userCenter.setMcId(i);
                userCenter.setMcName("user_" + i);

                MasterSelector masterSelector = new MasterSelector(userCenter, getInstance());
                masterSelectors.put(i, masterSelector);
                masterSelector.start();
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            for (Integer index : masterSelectors.keySet()) {
                masterSelectors.get(index).stop();
            }
        }
    }

    public MasterSelector(UserCenter server, ZkClient zkClient) {
        System.out.println(server + "开始争抢master权限");
        this.server = server;
        this.zkClient = zkClient;
        this.dataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {

            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                // 节点如果被删除，发起一个选举操作
                System.out.println(server.getMcName() + "->监听到了节点删除事件");
                chooseMaster();
            }
        };
    }

    /**
     * 选主操作
     */
    private void chooseMaster() {
        if (!isRunning) {
            System.out.println(server.getMcName() + "当前服务没有启动");
            return;
        }
        try {
            zkClient.createEphemeral(MASTER_PATH, server);
            // 把server赋给master
            master = server;
            System.out.println(master.getMcName() + "->我是master");
            // 定时器
            // master释放（master出现故障）
            executor.schedule(() -> {
                System.out.println("开始释放:" + master);
                releaseMaster();
            }, 2, TimeUnit.SECONDS);
        } catch (ZkNodeExistsException e) {
            // 表示节点已经存在
            // 再次获取master
            System.out.println(server.getMcName() + "->抢夺失败");
            UserCenter userCenter = zkClient.readData(MASTER_PATH, true);
            if (null == userCenter) {
                chooseMaster();
            } else {
                master = userCenter;
            }
        }
    }

    /**
     * 启动
     */
    private void start() {
        // 开始选举
        if (!isRunning) {
            System.out.println(server.getMcName() + "->启动操作");
            isRunning = true;
            // 注册节点事件
            zkClient.subscribeDataChanges(MASTER_PATH, dataListener);
            chooseMaster();
        }
    }

    /**
     * 停止
     */
    private void stop() {
        // 停止
        if (isRunning) {
            System.out.println(server + "->停止操作");
            isRunning = false;
            executor.shutdown();
            // 注册节点事件
            zkClient.unsubscribeDataChanges(MASTER_PATH, dataListener);
            releaseMaster();
        }
    }

    /**
     * 释放主
     */
    private void releaseMaster() {
        // 释放锁（故障模拟）
        // 判断当前是不是master,只有master才需要释放
        if (!isMaster()) {
            return;
        }
        System.out.println(server + "释放操作");
        zkClient.delete(MASTER_PATH);
        if (masterSelectors.size() > 1) {
            masterSelectors.get(server.getMcId()).stop();
        }
        masterSelectors.remove(server.getMcId());
    }

    private boolean isMaster() {
        // 判断是不是master
        UserCenter userCenter = zkClient.readData(MASTER_PATH, true);
        if (userCenter.getMcName().equals(server.getMcName())) {
            master = userCenter;
            return true;
        }
        return false;
    }

    private ZkClient zkClient;

    /**
     * 争抢的节点
     */
    private static final String MASTER_PATH = "/master";

    /**
     * 注册节点内容变化
     */
    private IZkDataListener dataListener;

    /**
     * 其他服务器
     */
    private UserCenter server;
    /**
     * master节点
     */
    private UserCenter master;

    private static boolean isRunning = false;

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

}

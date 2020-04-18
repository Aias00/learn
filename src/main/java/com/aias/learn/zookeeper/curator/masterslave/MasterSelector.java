package com.aias.learn.zookeeper.curator.masterslave;

import com.aias.learn.zookeeper.curator.CuratorUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/18
 *
 * 选主服务
 */
public class MasterSelector {
    private final static String MASTER_PATH = "/curator_master_path";

    public static void main(String[] args) throws IOException, InterruptedException {
        CuratorFramework framework = CuratorUtil.getInstance();
        LeaderSelector leaderSelector = new LeaderSelector(framework, MASTER_PATH, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                System.out.println("获得leader成功");
                TimeUnit.SECONDS.sleep(2);
            }
        });
        leaderSelector.autoRequeue();
        // 开始选举
        leaderSelector.start();
        TimeUnit.SECONDS.sleep(2);
    }
}

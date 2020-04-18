package com.aias.learn.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class CuratorEvent {
    /**
     * 三种watcher来做节点监听
     * PathCache    监视一个路径下节点的创建、删除、节点数据更新
     * NodeCache    监视一个节点的创建、更新、删除
     * TreeCache    PathCache+NodeCache合体（监视路径下的创建、更新、删除事件）缓存路径下所有子节点的数据
     */

    public static void main(String[] args) throws Exception {
        CuratorFramework framework = CuratorUtil.getInstance();
        // 节点变化nodeCache
//        NodeCache nodeCache = new NodeCache(framework, "/curator", false);
//        nodeCache.start(true);
//        nodeCache.getListenable()
//                 .addListener(() -> System.out.println("节点数据发生变化，变化后的结果:" + new String(nodeCache.getCurrentData().getData())));
//        framework.setData().forPath("/curator", "adsadasdas".getBytes());

        //
        framework.create().withMode(CreateMode.PERSISTENT).forPath("/event", "event".getBytes());
        TimeUnit.SECONDS.sleep(2);
        PathChildrenCache pathChildrenCache = new PathChildrenCache(framework, "/event", true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener((curatorFramework, pathChildrenCacheEvent) -> {
            switch (pathChildrenCacheEvent.getType()) {
                case CHILD_ADDED:
                    System.out.println("增加子节点");
                    break;
                case CHILD_UPDATED:
                    System.out.println("更新子节点");
                    break;
                case CHILD_REMOVED:
                    System.out.println("删除子节点");
                    break;
                default:
                    break;
            }
        });
        framework.create().withMode(CreateMode.EPHEMERAL).forPath("/event/event1", "event".getBytes());
        TimeUnit.SECONDS.sleep(2);
        framework.setData().forPath("/event/event1", "222".getBytes());
        TimeUnit.SECONDS.sleep(2);
        framework.delete().forPath("/event/event1");
    }

}

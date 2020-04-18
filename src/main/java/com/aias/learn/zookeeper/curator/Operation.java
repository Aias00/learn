package com.aias.learn.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;

/**
 * @author liuhy
 * @since 2020/4/18
 */
public class Operation {

    public static void main(String[] args) {
        CuratorFramework framework = CuratorUtil.getInstance();

        try {
            // 创建节点
//            String result = framework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
//                                     .forPath("/curator/curator1/curator11", "123".getBytes());
//            System.out.println(result);

            // 删除节点,默认情况下version为-1
//            framework.delete().deletingChildrenIfNeeded().forPath("/curator");
//            System.out.println("删除成功");

            // 取值
            Stat stat = new Stat();
            byte[] bytes = framework.getData().storingStatIn(stat).forPath("/curator/curator1/curator11");
            System.out.println(new String(bytes) + "--->" + stat);

            stat = framework.setData().forPath("/curator/curator1/curator11", "789".getBytes());
            System.out.println("--->" + stat);

            /**
             * 异步操作
             * 创建节点的事件是由线程池去处理的
             */
//            ExecutorService executorService = Executors.newFixedThreadPool(1);
//            CountDownLatch countDownLatch = new CountDownLatch(1);
//            framework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground(new
//           BackgroundCallback() {
//                @Override
//                public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
//                    System.out.println(Thread.currentThread().getName() + "--->resultCode:" + event.getResultCode()
//                   + "--->type:" + event.getType());
//                    countDownLatch.countDown();
//                }
//            }, executorService).forPath("/testPath/test1", "123".getBytes());
//            countDownLatch.await();
//            executorService.shutdown();
//            System.out.println("异步创建成功");

            /**
             * 事务操作（curator独有的）
             */
            Collection<CuratorTransactionResult> curatorTransactionResults =
                    framework.inTransaction().create().forPath("/trans", "111".getBytes()).and().setData().forPath(
                            "/curator/curator1/curator11", "123".getBytes()).and().commit();
            for (CuratorTransactionResult curatorTransactionResult : curatorTransactionResults) {
                System.out.println(curatorTransactionResult.getForPath() + ":" + curatorTransactionResult.getType());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

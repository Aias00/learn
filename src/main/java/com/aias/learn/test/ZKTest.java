package com.aias.learn.test;

import java.util.concurrent.CountDownLatch;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class ZKTest implements Runnable {

    // 模拟库存1个
    static int inventory = 1;

    private static final int NUM = 10;

    private static CountDownLatch countDownLatch = new CountDownLatch(NUM);

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {
            new Thread(new ZKTest()).start();
            countDownLatch.countDown();
        }
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            if (inventory > 0) {
                Thread.sleep(5);
                inventory--;
            }
            System.out.println(inventory);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

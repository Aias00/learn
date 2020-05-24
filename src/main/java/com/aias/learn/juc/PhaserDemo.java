package com.aias.learn.juc;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/5/24
 */
public class PhaserDemo {

    public static void main(String[] args) {
        Phaser phaser = new Phaser(4) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println(String.format("第%d次关卡准备完成", phase + 1));
                return phase == 3 || registeredParties == 0;
            }
        };

        new Thread(new PreTaskThread("加载地图数据", phaser)).start();
        new Thread(new PreTaskThread("加载人物模型", phaser)).start();
        new Thread(new PreTaskThread("加载背景音乐", phaser)).start();
        new Thread(new PreTaskThread("加载新手教程", phaser)).start();

    }

    static class PreTaskThread implements Runnable {

        private String task;

        private Phaser phaser;

        public PreTaskThread(String task, Phaser phaser) {
            this.task = task;
            this.phaser = phaser;
        }

        @Override
        public void run() {

            for (int i = 1; i < 4; ++i) {
                try {
                    // 第二关卡起不加载新手模块
                    if (i >= 2 && "加载新手教程".equalsIgnoreCase(task)) {
                        continue;
                    }

                    TimeUnit.MILLISECONDS.sleep(RandomUtils.nextLong(0, 1000));

                    System.out.println(String.format("关卡%d,需要加载%d个模块,当前任务:%s", i, phaser.getRegisteredParties(), task));

                    if (i == 1 && "加载新手教程".equalsIgnoreCase(task)) {
                        System.out.println("下一关卡移除[加载新手教程]模块");
                        phaser.arriveAndDeregister();
                    } else {
                        phaser.arriveAndAwaitAdvance();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

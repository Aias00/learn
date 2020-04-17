package com.aias.learn.pattern.singleton.lazy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author liuhy
 * @since 2020/4/17
 * 懒汉式单例类.在第一次调用的时候实例化自己
 */
public class Singleton {

    //1、第一步先将构造方法私有化
    private Singleton() {

    }

    //2、然后声明一个静态变量保存单例的引用
    private static Singleton INSTANCE = null;

    //3、通过提供一个静态方法来获得单例的引用
    //这个方法是线程不安全的
    public static Singleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }

    //启动100线程同时去抢CPU
    static int count = 100;

    static CountDownLatch countDownLatch = new CountDownLatch(count);

    //Set默认去去重的，set是本身线程不安全的
    //
    static final Set<Singleton> syncSet = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                syncSet.add(Singleton.getInstance());
            }).start();
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();//等待所有线程全部完成，最终输出结果
            System.out.println(syncSet.size());// 有几率打印出比1大的数字
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

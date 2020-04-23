package com.aias.learn.design.pattern.singleton.lazy;

/**
 * @author liuhy
 * @since 2020/4/17
 * 懒汉式单例.双重锁检查
 */
public class Singleton4 {
    //1、第一步先将构造方法私有化
    private Singleton4() {
    }

    //2、然后声明一个静态变量保存单例的引用
    //注意这里用到了volatile 关键字
    private static volatile Singleton4 INSTANCE = null;

    //3、通过提供一个静态方法来获得单例的引用
    //为了保证多线程环境下的另一种实现方式，双重锁检查
    //性能，第一次的时候会差一些
    public static Singleton4 getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton4.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton4();
                }
            }
        }
        return INSTANCE;
    }

}

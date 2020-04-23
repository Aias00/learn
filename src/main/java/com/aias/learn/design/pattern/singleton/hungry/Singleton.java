package com.aias.learn.design.pattern.singleton.hungry;

/**
 * @author liuhy
 * @since 2020/4/17
 * 饿汉式单例类.在类初始化时，已经自行实例化
 */
public class Singleton {

    private static Singleton INSTANCE = new Singleton();

    private Singleton() {

    }

    public static Singleton getInstance() {
        return INSTANCE;
    }

}

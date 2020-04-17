package com.aias.learn.pattern.singleton.enums;

/**
 * @author liuhy
 * @since 2020/4/17
 * 枚举式单例 比较少见
 * 线程安全、调用效率高、没有延时加载。并且具有天然的防止反射和反序列化漏洞
 */
public enum Singleton {
    INSTANCE;

    public void getInstance() {
    }
}

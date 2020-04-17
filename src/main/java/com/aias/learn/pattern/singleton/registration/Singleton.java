package com.aias.learn.pattern.singleton.registration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuhy
 * @since 2020/4/17
 * 登记式单例
 * 类似Spring里面的方法，将类名注册，下次从里面直接获取
 */
public class Singleton {
    private static Map<String, Singleton> map = new HashMap<String, Singleton>();

    static {
        Singleton INSTANCE = new Singleton();
        map.put(INSTANCE.getClass().getName(), INSTANCE);
    }

    private Singleton() {

    }

    // 静态工厂方法,返还此类惟一的实例
    public static Singleton getInstance(String name) {
        if (name == null) {
            name = Singleton.class.getName();
        }
        if (map.get(name) == null) {
            try {
                map.put(name, (Singleton) Class.forName(name).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return map.get(name);
    }

}

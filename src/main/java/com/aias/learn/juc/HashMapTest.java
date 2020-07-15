package com.aias.learn.juc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuhy
 * @since 2020/7/15
 * HashMap多线程修改已经存在的key的值不会报错
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "banana");
        map.put("2", "orange");
        map.put("3", "apple");

        for (int i = 1; i < 4; i++) {
            final int index = i;
            new Thread(() -> map.put(String.valueOf(index), Thread.currentThread().getName())).start();
        }

        for (String s : map.keySet()) {
            System.out.println(map.get(s));
        }
    }

}

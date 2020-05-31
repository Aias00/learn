package com.aias.learn.juc.stream;

import java.util.stream.Stream;

/**
 * @author liuhy
 * @since 2020/5/30
 */
public class StreamDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce((a, b) -> {
            System.out.println(String.format("%s: %d + %d = %d", Thread.currentThread().getName(), a, b, a + b));
            return a + b;
        }).ifPresent(System.out::println);
        System.out.println("耗时:" + (System.currentTimeMillis() - start));
    }

}

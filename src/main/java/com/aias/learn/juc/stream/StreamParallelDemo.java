package com.aias.learn.juc.stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Stream 并行计算
 *
 * @author liuhy
 * @since 2020/5/30
 */
public class StreamParallelDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
              .parallel()
              .reduce((a, b) -> {
                  System.out.println(String.format("%s: %d + %d = %d", Thread.currentThread().getName(), a, b, a + b));
                  return a + b;
              })
              .ifPresent(System.out::println);
        System.out.println("耗时:" + (System.currentTimeMillis() - start));

        System.out.println(String.format("本计算机的核心数: %d", Runtime.getRuntime().availableProcessors()));
        Random random = new Random();
        List<Integer> list = Lists.newArrayList();
        for (int i = 0; i < 1000_0000; i++) {
            list.add(random.nextInt(100));
        }

        long preTime = System.currentTimeMillis();
        list.stream().reduce(Integer::sum).ifPresent(System.out::println);
        long midTime = System.currentTimeMillis();
        System.out.println("单线程计算耗时:" + (midTime - preTime));
        list.stream().parallel().reduce(Integer::sum).ifPresent(System.out::println);
        System.out.println("多线程计算耗时:" + (System.currentTimeMillis() - preTime));

    }

}

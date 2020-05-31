package com.aias.learn.juc.forkjoin;

/**
 * 普通递归实现斐波那契
 *
 * @author liuhy
 * @since 2020/5/30
 */
public class PlainRecursion {

    public static int plainRecursion(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return plainRecursion(n - 1) + plainRecursion(n - 2);
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int result = plainRecursion(40);
        System.out.println("结果:" + result);
        System.out.println(String.format("耗时: %d mills", System.currentTimeMillis() - start));
    }

}

package com.aias.learn.juc.forkjoin;

/**
 * 循环实现斐波那契
 *
 * @author liuhy
 * @since 2020/5/30
 */
public class ComputeFibonacci {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int result = fibonacci(40);
        System.out.println("结果:" + result);
        System.out.println(String.format("耗时: %d mills", System.currentTimeMillis() - start));
    }

    public static int fibonacci(int n) {

        if (n <= 1) {
            return n;
        } else {
            int first = 1;
            int second = 1;
            int third = 0;
            for (int i = 3; i <= n; i++) {
                third = first + second;
                first = second;
                second = third;
            }
            return third;
        }

    }

}

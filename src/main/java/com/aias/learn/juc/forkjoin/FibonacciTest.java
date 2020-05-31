package com.aias.learn.juc.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoin实现斐波那契数列
 *
 * @author liuhy
 * @since 2020/5/30
 */
public class FibonacciTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println("CPU核心数" + Runtime.getRuntime().availableProcessors());
        long start = System.currentTimeMillis();
        Fibonacci fibonacci = new Fibonacci(40);
        Future<Integer> future = forkJoinPool.submit(fibonacci);

        System.out.println(future.get());
        System.out.println(String.format("耗时: %d mills", System.currentTimeMillis() - start));

    }

    static class Fibonacci extends RecursiveTask<Integer> {
        private static final long serialVersionUID = 1186488354759811459L;
        int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        // 主要实现逻辑
        @Override
        protected Integer compute() {
            // 先假设 n >= 0
            if (n <= 1) {
                return n;
            } else {

                Fibonacci f1 = new Fibonacci(n - 1);
                f1.fork();
                Fibonacci f2 = new Fibonacci(n - 2);
                f2.fork();
                return f1.join() + f2.join();
            }
        }
    }

}

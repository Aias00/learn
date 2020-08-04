package com.aias.learn.juc.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/6/28
 */
public class CatchThreadPoolException {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                4,
                1L,
                TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(1024),
                r -> {
                    Thread thread = new Thread(r);
                    //设置线程异常处理器
                    thread.setUncaughtExceptionHandler(new ThreadExceptionHandler());
                    return thread;
                }
        ){
            /**
             * 捕获{@code FutureTask<?>}抛出的异常
             *
             * @param r
             * @param t
             */
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                if (r instanceof FutureTask<?>) {
                    try {
                        //get()的时候会将异常内的异常抛出
                        ((FutureTask<?>) r).get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    } catch (ExecutionException e) {
                        System.out.println("捕获到线程的异常返回值" + e.getMessage());
                    }
                }
                //Throwable t永远为null，拿不到异常信息
                //log.error("afterExecute中捕获到异常，", t);
            }
        };

        threadPoolExecutor.execute(() -> {
                    throw new RuntimeException("execute()发生异常");
                }
        );

        threadPoolExecutor.submit((Runnable) () -> {
            throw new RuntimeException("submit.run()发生异常");
        });

        threadPoolExecutor.submit((Callable<String>) () -> {
            throw new RuntimeException("submit.call()发生异常");
        }).get();
        threadPoolExecutor.shutdown();
    }
}

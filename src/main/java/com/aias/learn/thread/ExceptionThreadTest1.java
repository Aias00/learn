package com.aias.learn.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/6/28
 */
public class ExceptionThreadTest1 implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException("子线程发生了异常...");
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new ExceptionThreadTest1());
        //为指定线程设置特定的异常处理器
        thread1.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        thread1.start();
        TimeUnit.MILLISECONDS.sleep(200L);
        new Thread(new ExceptionThreadTest1()).start();
        TimeUnit.MILLISECONDS.sleep(200L);
        new Thread(new ExceptionThreadTest1()).start();
        TimeUnit.MILLISECONDS.sleep(200L);
        new Thread(new ExceptionThreadTest1()).start();
        TimeUnit.MILLISECONDS.sleep(200L);
    }

}

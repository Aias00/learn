package com.aias.learn.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/6/28
 */
public class ExceptionThreadTest implements Runnable{
    @Override
    public void run() {
        throw new RuntimeException("子线程发生了异常...");
    }

    public static void main(String[] args) throws InterruptedException {

        Thread.setDefaultUncaughtExceptionHandler(new ThreadExceptionHandler());

        new Thread(new ExceptionThreadTest()).start();
        TimeUnit.MILLISECONDS.sleep(200L);
        new Thread(new ExceptionThreadTest()).start();
        TimeUnit.MILLISECONDS.sleep(200L);
        new Thread(new ExceptionThreadTest()).start();
        TimeUnit.MILLISECONDS.sleep(200L);
        new Thread(new ExceptionThreadTest()).start();
        TimeUnit.MILLISECONDS.sleep(200L);
    }

}

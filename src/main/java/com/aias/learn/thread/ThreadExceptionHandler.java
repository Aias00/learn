package com.aias.learn.thread;

/**
 * @author liuhy
 * @since 2020/6/28
 */
public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("捕获到线程发生异常:" + t.getName() + " :" + e.getMessage());
    }
}

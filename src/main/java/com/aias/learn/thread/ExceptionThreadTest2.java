package com.aias.learn.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/6/28
 */
public class ExceptionThreadTest2 implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException("子线程发生了异常...");
    }

    public static void main(String[] args) throws InterruptedException {

        ThreadGroup threadGroup = new ThreadGroup("只知道抛出异常的线程组...") {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
//                super.uncaughtException(t, e);
                System.out.println("线程组内捕获到线程" + t.getName() + " :" + e.getMessage());
            }
        };
        ExceptionThreadTest2 exceptionInThreadGroup = new ExceptionThreadTest2();

        new Thread(threadGroup, exceptionInThreadGroup, "线程1").start();
        TimeUnit.MILLISECONDS.sleep(300L);

        //优先获取绑定在thread对象上的异常处理器
        Thread thread = new Thread(threadGroup, exceptionInThreadGroup, "线程2");
        thread.setUncaughtExceptionHandler(new ThreadExceptionHandler());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(300L);

        new Thread(threadGroup, exceptionInThreadGroup, "线程3").start();
    }

}

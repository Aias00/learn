package com.aias.learn.juc.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/5/30
 * 延时/周期线程池
 */
public class ThreadPool {

    private static final ScheduledThreadPoolExecutor EXECUTOR =
            new ScheduledThreadPoolExecutor(1, Executors.defaultThreadFactory());

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static void main(String[] args) {
        EXECUTOR.scheduleWithFixedDelay(() -> {
            if(haveMsgCurrent()){
                System.out.println(sdf.format(new Date()));
                System.out.println("收到消息了");
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    private static boolean haveMsgCurrent() {
        // 模拟查询 一直返回true
        return true;
    }

}

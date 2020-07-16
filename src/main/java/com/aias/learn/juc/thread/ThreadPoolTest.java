package com.aias.learn.juc.thread;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/7/16
 * 自定义线程池
 */
public class ThreadPoolTest {

    /**
     * 核心线程数
     */
    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    /**
     * 线程池名称格式
     */
    private static final String THREAD_POOL_NAME = "ThreadPool-%d";

    /**
     * 线程工厂名称
     */
    private static final ThreadFactory FACTORY = new BasicThreadFactory.Builder().namingPattern(THREAD_POOL_NAME)
                                                                                 .daemon(true).build();
    /**
     * 默认队列大小
     */
    private static final int DEFAULT_SIZE = 500;

    /**
     * 默认线程存活时间
     */
    private static final long DEFAULT_KEEP_ALIVE = 60L;

    /**
     * Executor
     */
    private static ExecutorService executor;

    /**
     * 执行队列
     */
    private static BlockingQueue<Runnable> executeQueue = new ArrayBlockingQueue<>(DEFAULT_SIZE);

    static {
        executor = new ThreadPoolExecutor(CORE_SIZE, CORE_SIZE * 4, DEFAULT_KEEP_ALIVE,
                TimeUnit.SECONDS, executeQueue, FACTORY);
    }
}

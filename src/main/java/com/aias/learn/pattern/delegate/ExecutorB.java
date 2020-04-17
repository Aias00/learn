package com.aias.learn.pattern.delegate;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class ExecutorB implements Executor {
    @Override
    public void work() {
        System.out.println("员工B开始执行任务");
    }
}

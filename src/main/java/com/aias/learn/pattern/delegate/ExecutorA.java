package com.aias.learn.pattern.delegate;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class ExecutorA implements Executor {
    @Override
    public void work() {
        System.out.println("员工A开始执行任务");
    }
}

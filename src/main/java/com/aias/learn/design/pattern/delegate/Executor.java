package com.aias.learn.design.pattern.delegate;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public interface Executor {

    // 普通员工执行任务
    // 在公司内规定:员工执行任务必须在一周内完成
    void work();
}

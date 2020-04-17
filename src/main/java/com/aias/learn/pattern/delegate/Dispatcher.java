package com.aias.learn.pattern.delegate;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class Dispatcher implements Executor {
    // 持有员工对象的实例
    Executor executor;

    public Dispatcher(Executor executor) {
        this.executor = executor;
    }

    // 管理者,工作职责是分配任务让员工去执行
    @Override
    public void work() {
        this.executor.work();
    }

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher(new ExecutorA());
        dispatcher.work();
    }
}

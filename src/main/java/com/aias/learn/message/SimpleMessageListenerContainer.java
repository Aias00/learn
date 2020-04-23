package com.aias.learn.message;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liuhy
 * @since 2020/4/22
 * 简单消息监听器容器
 */
public class SimpleMessageListenerContainer implements MessageListenerContainer {
    /**
     * 使用一个map存放消息和监听类的对应关系 监听类存放数组 便于多个监听类监听同一个消息
     */
    private static Map<String, List<MessageListener>> messageListenerMap = Maps.newConcurrentMap();

    /**
     * 启用线程池 支持同时处理消息
     */
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 500, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void registerMessageListener(String messageClassName, MessageListener messageListener) {
        List<MessageListener> listeners = messageListenerMap.computeIfAbsent(messageClassName, k -> Lists.newArrayList());
        listeners.add(messageListener);
    }

}

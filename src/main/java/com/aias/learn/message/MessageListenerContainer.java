package com.aias.learn.message;

/**
 * @author liuhy
 * @since 2020/4/22
 * 消息监听器容器 接口
 */
public interface MessageListenerContainer {
    /**
     * 注册消息监听器
     *
     * @param messageClassName 消息类名
     * @param messageListener  消息监听器
     */
    void registerMessageListener(String messageClassName, MessageListener messageListener);

    /**
     * 有消息要通知监听者
     *
     * @param message 消息对象
     */
    void notify(final AbstractMessage message);

}

package com.aias.learn.message;

/**
 * @author liuhy
 * @since 2020/4/22
 * 内存实现消息通知  消息监听接口
 */
public interface MessageListener {

    /**
     * 接收消息方法
     *
     * @param msg 接收的消息
     */
    void onMessage(AbstractMessage msg);
}

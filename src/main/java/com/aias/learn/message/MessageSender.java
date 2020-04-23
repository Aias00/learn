package com.aias.learn.message;

/**
 * @author liuhy
 * @since 2020/4/22
 * 内存实现消息通知 sender接口
 */
public interface MessageSender {
    /**
     * 消息发送
     *
     * @param msg 发送内容
     */
    void send(AbstractMessage msg);

}

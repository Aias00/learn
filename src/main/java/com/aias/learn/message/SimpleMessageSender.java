package com.aias.learn.message;

/**
 * @author liuhy
 * @since 2020/4/22
 * 简单消息发送实现类
 */
public class SimpleMessageSender implements MessageSender {

    private MessageListenerContainer container;

    public SimpleMessageSender(MessageListenerContainer container) {
        this.container = container;
    }

    @Override
    public void send(AbstractMessage msg) {
        System.out.println("开始发送消息");
        container.notify(msg);
    }
}

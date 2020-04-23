package com.aias.learn.message;

/**
 * @author liuhy
 * @since 2020/4/23
 */
public class MessageTest {
    public static void main(String[] args) {
        MessageListenerContainer container = new SimpleMessageListenerContainer();

        container.registerMessageListener("com.aias.learn.message.SimpleMessage", new SimpleMessageListener());

        SimpleMessageSender simpleMessageSender = new SimpleMessageSender(container);
        SimpleMessage message = new SimpleMessage();
        message.setData("这是消息报文体");
        simpleMessageSender.send(message);

    }
}

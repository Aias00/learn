package com.aias.learn.message;

/**
 * @author liuhy
 * @since 2020/4/23
 */
public class MessageTest {
    public static void main(String[] args) {
        MessageListenerContainer container = new SimpleMessageListenerContainer();


        MessageListener listener1 = new SimpleMessageListener("监听器1");
        MessageListener listener2 = new SimpleMessageListener("监听器2");

        container.registerMessageListener("com.aias.learn.message.SimpleMessage", listener1);
        container.registerMessageListener("com.aias.learn.message.SimpleMessage", listener2);

        SimpleMessageSender simpleMessageSender = new SimpleMessageSender(container);
        SimpleMessage message = new SimpleMessage();
        message.setData("这是消息报文体");
        simpleMessageSender.send(message);

        container.unRegisterMessageListener("com.aias.learn.message.SimpleMessage", listener2);

        message.setData("这是新的报文体");
        simpleMessageSender.send(message);
    }
}

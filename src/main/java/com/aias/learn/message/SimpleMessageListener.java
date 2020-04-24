package com.aias.learn.message;

/**
 * @author liuhy
 * @since 2020/4/22
 * 简单消息监听实现类
 */
public class SimpleMessageListener implements MessageListener {

    private String name;

    public SimpleMessageListener(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(AbstractMessage msg) {
        System.out.println(name + " -> 接收到了消息");
        if (msg instanceof SimpleMessage) {
            SimpleMessage simpleMessage = (SimpleMessage) msg;
            System.out.println(simpleMessage.getData());
            System.out.println(simpleMessage.getTimeStamp());
        }
    }
}

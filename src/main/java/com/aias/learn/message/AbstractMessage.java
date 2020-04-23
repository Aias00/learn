package com.aias.learn.message;

import java.io.Serializable;

/**
 * @author liuhy
 * @since 2020/4/22
 * 简单的消息通知  抽象类,防止实例化此类对象
 */
public abstract class AbstractMessage implements Serializable {

    private static final long serialVersionUID = -7313600907030593631L;

    private long timeStamp = System.currentTimeMillis();

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

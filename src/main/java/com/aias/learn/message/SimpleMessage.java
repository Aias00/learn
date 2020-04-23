package com.aias.learn.message;

/**
 * @author liuhy
 * @since 2020/4/22
 * 简单消息实体类
 */
public class SimpleMessage extends AbstractMessage {
    private static final long serialVersionUID = 1987292255770855963L;

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

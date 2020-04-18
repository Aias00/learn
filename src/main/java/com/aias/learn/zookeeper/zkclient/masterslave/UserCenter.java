package com.aias.learn.zookeeper.zkclient.masterslave;

import java.io.Serializable;

/**
 * @author liuhy
 * @since 2020/4/18
 * 模拟用户节点
 */
public class UserCenter implements Serializable {

    /**
     * 机器信息
     */
    private int mcId;
    /**
     * 机器名称
     */
    private String mcName;

    @Override
    public String toString() {
        return mcName + "->";
    }

    public int getMcId() {
        return mcId;
    }

    public void setMcId(int mcId) {
        this.mcId = mcId;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }
}

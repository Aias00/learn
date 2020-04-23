package com.aias.learn.design.pattern.factory;

/**
 * @author liuhy
 * @since 2020/4/17
 * 产品接口
 * 汽车需要满足一定的标准
 */
public interface Car {
    /**
     * 获取品牌
     *
     * @return
     */
    String getBrand();
}

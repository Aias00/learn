package com.aias.learn.design.pattern.decorator;

/**
 * @author liuhy
 * @since 2020/4/23
 * 装饰者模式
 */
public class BatterCake extends AbstractBatterCake {

    @Override
    public String getDesc() {
        return "煎饼";
    }

    @Override
    public int cost() {
        return 5;
    }
}

package com.aias.learn.design.pattern.template;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class Tea extends Beverage {
    @Override
    public void pourInCup() {
        System.out.println("将茶叶放入杯中");
    }

    @Override
    public void addCondiments() {
        System.out.println("添加蜂蜜");
    }
}

package com.aias.learn.pattern.template;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class Coffee extends Beverage {
    @Override
    public void pourInCup() {
        System.out.println("将咖啡倒入杯中");
    }

    @Override
    public void addCondiments() {
        System.out.println("添加牛奶和糖");
    }
}

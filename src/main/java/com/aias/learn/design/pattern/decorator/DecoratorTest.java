package com.aias.learn.design.pattern.decorator;

/**
 * @author liuhy
 * @since 2020/4/23
 */
public class DecoratorTest {

    public static void main(String[] args) {
        AbstractBatterCake batterCake = new BatterCake();
        System.out.println(batterCake.getDesc() + " 销售价格:" + batterCake.cost());
        batterCake = new Egg(batterCake);
        System.out.println(batterCake.getDesc() + " 销售价格:" + batterCake.cost());
        batterCake = new Egg(batterCake);
        System.out.println(batterCake.getDesc() + " 销售价格:" + batterCake.cost());
        batterCake = new Sausage(batterCake);
        System.out.println(batterCake.getDesc() + " 销售价格:" + batterCake.cost());

    }
}

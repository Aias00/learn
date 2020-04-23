package com.aias.learn.design.pattern.decorator;

/**
 * @author liuhy
 * @since 2020/4/23
 */
public class Egg extends AbstractDecorator {
    public Egg(AbstractBatterCake batterCake) {
        super(batterCake);
    }

    @Override
    public String getDesc() {
        return super.getDesc() + " +一个蛋";
    }

    @Override
    public int cost() {
        return super.cost() + 1;
    }
}

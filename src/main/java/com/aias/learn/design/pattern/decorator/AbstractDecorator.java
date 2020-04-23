package com.aias.learn.design.pattern.decorator;

/**
 * @author liuhy
 * @since 2020/4/23
 */
public class AbstractDecorator extends AbstractBatterCake {
    private AbstractBatterCake batterCake;

    public AbstractDecorator(AbstractBatterCake batterCake) {
        this.batterCake = batterCake;
    }

    @Override
    public String getDesc() {
        return batterCake.getDesc();
    }

    @Override
    public int cost() {
        return batterCake.cost();
    }
}

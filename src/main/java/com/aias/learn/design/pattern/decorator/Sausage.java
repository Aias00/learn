package com.aias.learn.design.pattern.decorator;

/**
 * @author liuhy
 * @since 2020/4/23
 */
public class Sausage extends AbstractDecorator {
    public Sausage(AbstractBatterCake batterCake) {
        super(batterCake);
    }

    @Override
    public String getDesc() {
        return super.getDesc() + " +一根香肠";
    }

    @Override
    public int cost() {
        return super.cost() + 3;
    }
}

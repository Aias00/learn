package com.aias.learn.design.pattern.strategy;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class MultiOperation implements Operation {
    @Override
    public int operate(int num1, int num2) {
        return num1 * num2;
    }
}

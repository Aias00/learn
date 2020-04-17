package com.aias.learn.pattern.factory.abstracts;

import com.aias.learn.pattern.factory.Benz;
import com.aias.learn.pattern.factory.Car;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class BenzFactory extends AbstractFactory {
    @Override
    public Car getCar() {
        return new Benz();
    }
}

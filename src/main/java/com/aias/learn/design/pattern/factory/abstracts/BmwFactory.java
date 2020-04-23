package com.aias.learn.design.pattern.factory.abstracts;

import com.aias.learn.design.pattern.factory.Bmw;
import com.aias.learn.design.pattern.factory.Car;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class BmwFactory extends AbstractFactory {
    @Override
    public Car getCar() {
        return new Bmw();
    }
}

package com.aias.learn.pattern.factory.abstracts;

import com.aias.learn.pattern.factory.Bmw;
import com.aias.learn.pattern.factory.Car;

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

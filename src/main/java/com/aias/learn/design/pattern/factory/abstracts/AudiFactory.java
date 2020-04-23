package com.aias.learn.design.pattern.factory.abstracts;

import com.aias.learn.design.pattern.factory.Audi;
import com.aias.learn.design.pattern.factory.Car;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class AudiFactory extends AbstractFactory {
    @Override
    public Car getCar() {
        return new Audi();
    }
}

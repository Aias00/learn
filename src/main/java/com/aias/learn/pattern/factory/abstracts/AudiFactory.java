package com.aias.learn.pattern.factory.abstracts;

import com.aias.learn.pattern.factory.Audi;
import com.aias.learn.pattern.factory.Car;

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

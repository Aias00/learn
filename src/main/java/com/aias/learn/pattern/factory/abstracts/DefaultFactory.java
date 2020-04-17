package com.aias.learn.pattern.factory.abstracts;

import com.aias.learn.pattern.factory.Car;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class DefaultFactory extends AbstractFactory {
    private AudiFactory audiFactory = new AudiFactory();

    @Override
    public Car getCar() {
        return audiFactory.getCar();
    }
}

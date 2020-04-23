package com.aias.learn.design.pattern.factory.abstracts;

import com.aias.learn.design.pattern.factory.Car;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public abstract class AbstractFactory implements Factory {

    public abstract Car getCar();

    //这段代码就是动态配置的功能
    //固定模式的委派
    @Override
    public Car getCar(String name) {
        if ("BMW".equalsIgnoreCase(name)) {
            return new BmwFactory().getCar();
        } else if ("Benz".equalsIgnoreCase(name)) {
            return new BenzFactory().getCar();
        } else if ("Audi".equalsIgnoreCase(name)) {
            return new AudiFactory().getCar();
        } else {
            return new DefaultFactory().getCar();
        }
    }

}

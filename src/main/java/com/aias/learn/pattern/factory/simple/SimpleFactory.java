package com.aias.learn.pattern.factory.simple;

import com.aias.learn.pattern.factory.Audi;
import com.aias.learn.pattern.factory.Benz;
import com.aias.learn.pattern.factory.Bmw;
import com.aias.learn.pattern.factory.Car;
import com.aias.learn.pattern.factory.abstracts.Factory;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class SimpleFactory implements Factory {

    // 一个工厂能制造所有品牌的车辆
    @Override
    public Car getCar(String name) {
        if ("BMW".equalsIgnoreCase(name)) {
            return new Bmw();
        } else if ("AUDI".equalsIgnoreCase(name)) {
            return new Audi();
        } else if ("BENZ".equalsIgnoreCase(name)) {
            return new Benz();
        }
        System.out.println("no this car " + name);
        return null;
    }

}

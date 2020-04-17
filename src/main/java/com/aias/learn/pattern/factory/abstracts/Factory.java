package com.aias.learn.pattern.factory.abstracts;

import com.aias.learn.pattern.factory.Car;

/**
 * @author liuhy
 * @since 2020/4/17
 * 工厂接口，就定义了所有工厂的执行标准
 */
public interface Factory {

    Car getCar(String name);
}

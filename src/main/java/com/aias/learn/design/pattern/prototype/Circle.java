package com.aias.learn.design.pattern.prototype;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class Circle extends Shape {

    public Circle() {
        type = "Circle";
    }

    @Override
    void draw() {
        System.out.println("circle");
    }
}

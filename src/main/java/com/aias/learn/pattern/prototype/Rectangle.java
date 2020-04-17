package com.aias.learn.pattern.prototype;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class Rectangle extends Shape {
    public Rectangle() {
        type = "Rectangle";
    }

    @Override
    void draw() {
        System.out.println("Rectangle");
    }
}

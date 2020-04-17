package com.aias.learn.pattern.prototype;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class Square extends Shape {
    public Square() {
        type = "Square";
    }

    @Override
    void draw() {
        System.out.println("Square");
    }
}

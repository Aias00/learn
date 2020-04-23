package com.aias.learn.design.pattern.flyweight;

/**
 * @author liuhy
 * @since 2020/4/23
 */
public class ConcreteWebSite extends WebSite {

    private String name;

    public ConcreteWebSite(String name) {
        this.name = name;
    }

    @Override
    public void visit() {
        System.out.println("访问了: " + name);
    }
}

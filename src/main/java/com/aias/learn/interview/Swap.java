package com.aias.learn.interview;

import java.lang.reflect.Field;

/**
 * @author liuhy
 * @since 2020/4/17
 * 交换A B 的值
 */
public class Swap {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a = 1;
        Integer b = 2;
        System.out.println("a:"+a);
        System.out.println("b:"+b);
        swap(a,b);
        System.out.println("a:"+a);
        System.out.println("b:"+b);
    }

    /**
     * 交换AB的值
     * @param a
     * @param b
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static void swap(Integer a,Integer b) throws NoSuchFieldException, IllegalAccessException {
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        Integer temp = a;
        field.set(a, b);
        field.set(b,temp);
    }
}

package com.aias.learn.pattern.strategy;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class Context {

    private Operation operation;

    public Context(Operation operation) {
        this.operation = operation;
    }

    public int exec(int num1, int num2) {
        return operation.operate(num1, num2);
    }

    public static void main(String[] args) {
        Context context = new Context(new AddOperation());
        System.out.println(context.exec(1, 2));
        context = new Context(new SubOperation());
        System.out.println(context.exec(4, 2));
        context = new Context(new MultiOperation());
        System.out.println(context.exec(4, 2));

    }

}

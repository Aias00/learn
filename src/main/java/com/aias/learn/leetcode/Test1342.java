package com.aias.learn.leetcode;

/**
 * @author liuhy
 * @since 2020/4/19
 * 将数字变成 0 的操作次数
 * https://leetcode-cn.com/problems/number-of-steps-to-reduce-a-number-to-zero/
 */
public class Test1342 {

    public static void main(String[] args) {
        int num = 8;
        System.out.println(numberOfSteps(num));
    }

    public static int numberOfSteps(int num) {
        int count = 0;
        while (num > 0) {
            count++;
            if (num % 2 == 0) {
                num = num / 2;
                continue;
            }
            num -= 1;
        }
        return count;
    }
}

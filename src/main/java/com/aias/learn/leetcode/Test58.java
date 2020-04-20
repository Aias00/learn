package com.aias.learn.leetcode;

/**
 * @author liuhy
 * @since 2020/4/19
 * 字符串左旋转
 * https://leetcode-cn.com/problems/zuo-xuan-zhuan-zi-fu-chuan-lcof/
 */
public class Test58 {

    public static void main(String[] args) {
        String s = "abcdefg";
        int n = 2;
        // "cdefgab"
        System.out.println(reverseLeftWords(s, n));
    }

    public static String reverseLeftWords(String s, int n) {

        return s.substring(n).concat(s.substring(0, n));
    }
}

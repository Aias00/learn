package com.aias.learn.leetcode;

/**
 * @author liuhy
 * @since 2020/4/19
 * 统计位数为偶数的数字
 * https://leetcode-cn.com/problems/find-numbers-with-even-number-of-digits/
 */
public class Test1295 {

    public static void main(String[] args) {
        int[] nums = new int[]{555,901,482,1771};
        System.out.println(findNumbers(nums));
    }

    public static int findNumbers(int[] nums) {
        int count = 0;
        for (int num : nums) {
            if (String.valueOf(num).length() % 2 == 0) {
                count++;
            }
        }
        return count;
    }
}

package com.aias.learn.leetcode;

/**
 * @author liuhy
 * @since 2020/4/19
 * 猜数字
 * https://leetcode-cn.com/problems/guess-numbers/
 */
public class GuessNumber {

    public static void main(String[] args) {
        int[] guess = new int[]{1, 2, 3};
        int[] answer = new int[]{1, 2, 3};
        System.out.println(game(guess, answer));
    }

    public static int game(int[] guess, int[] answer) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            count += guess[i] == answer[i] ? 1 : 0;
        }
        return count;
    }
}

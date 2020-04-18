package com.aias.learn.sort;

/**
 * @author liuhy
 * @since 2020/4/17
 */
public class Sort {

    public static void main(String[] args) {
        int[] a = new int[]{1, 8, 3, 2, 4, 6};
        shellSort(a);
        for (int value : a) {
            System.out.println(value);
        }
    }

    /**
     * 选择排序
     * 平均时间复杂度 O(n2)
     * <p>
     * 在长度为N的无序数组中，第一次遍历n-1个数，找到最小的数值与第一个元素交换；
     * 第二次遍历n-2个数，找到最小的数值与第二个元素交换；
     * 。。。
     * 第n-1次遍历，找到最小的数值与第n-1个元素交换，排序完成。
     *
     * @param arr
     */
    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            // 从 i+1 开始循环
            for (int j = i + 1; j < arr.length; j++) {
                // 比较 arr[index] 和 arr[j] 获得更小值的下标
                index = arr[j] < arr[index] ? j : index;
            }
            // 更换位置 将比较小的值放到前面
            if (index != i) {
                arr[index] = arr[index] + arr[i];
                arr[i] = arr[index] - arr[i];
                arr[index] = arr[index] - arr[i];
            }
        }
    }

    /**
     * 冒泡排序
     * 平均时间复杂度 O(n2)
     * 两个数比较大小，较大的数下沉，较小的数冒起来
     * <p>
     * 比较相邻的两个数据，如果第二个数小，就交换位置。
     * 从后向前两两比较，一直到比较最前两个数据。最终最小数被交换到起始的位置，这样第一个最小数的位置就排好了。
     * 继续重复上述过程，依次将第2.3...n-1个最小数排好位置。
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        int i, j;
        for (i = 0; i < arr.length; i++) {  //表示次数，一共 arr.length-1 次。
            for (j = 0; j < arr.length - 1 - i; j++) {
                // 判断如果当前值比下一个值大 替换两个值的位置
                if (arr[j] > arr[j + 1]) {
                    arr[j] = arr[j] + arr[j + 1];
                    arr[j + 1] = arr[j] - arr[j + 1];
                    arr[j] = arr[j] - arr[j + 1];
                }
            }
        }
    }

    /**
     * 插入排序
     * O(n2)
     * <p>
     * 在要排序的一组数中，假定前n-1个数已经排好序，现在将第n个数插到前面的有序数列中，使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序。
     *
     * @param arr
     */
    public static void insertSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    arr[j] = arr[j - 1] + arr[j];
                    arr[j - 1] = arr[j] - arr[j - 1];
                    arr[j] = arr[j] - arr[j - 1];
                } else {         //不需要交换
                    break;
                }
            }
        }
    }

    /**
     * 希尔排序
     * O(n1.5)
     * <p>
     * 在要排序的一组数中，根据某一增量分为若干子序列，并对子序列分别进行插入排序。
     * 然后逐渐将增量减小,并重复上述过程。直至增量为1,此时数据序列基本有序,最后进行插入排序。
     *
     * @param arr
     */
    public static void shellSort(int[] arr) {
        int temp = 0;
        int incre = arr.length;

        do {
            incre = incre / 2;

            for (int k = 0; k < incre; k++) {    //根据增量分为若干子序列

                for (int i = k + incre; i < arr.length; i += incre) {

                    for (int j = i; j > k; j -= incre) {
                        if (arr[j] < arr[j - incre]) {
                            arr[j] = arr[j - incre] + arr[j];
                            arr[j - incre] = arr[j] - arr[j - incre];
                            arr[j] = arr[j] - arr[j - incre];
                        } else {
                            break;
                        }
                    }
                }
            }

        } while (incre != 1);
    }

    /**
     * 快速排序
     * O(n1.5)
     * <p>
     * 先从数列中取出一个数作为key值；
     * 将比这个数小的数全部放在它的左边，大于或等于它的数全部放在它的右边；
     * 对左右两个小数列重复第二步，直至各区间只有1个数。
     *
     * @param arr
     */
    public static void quickSort(int[] arr, int l, int r) {
        if (l >= r)
            return;

        int i = l;
        int j = r;
        int key = arr[l];//选择第一个数为key

        while (i < j) {

            while (i < j && arr[j] >= key)//从右向左找第一个小于key的值
            {
                j--;
            }
            if (i < j) {
                arr[i] = arr[j];
                i++;
            }

            while (i < j && arr[i] < key)//从左向右找第一个大于key的值
            {
                i++;
            }

            if (i < j) {
                arr[j] = arr[i];
                j--;
            }
        }
        //i == j
        arr[i] = key;
        quickSort(arr, l, i - 1);//递归调用
        quickSort(arr, i + 1, r);//递归调用
    }

    /**
     * 归并排序
     * O(N*logN)
     * <p>
     * 将有序数组a[]和b[]合并到c[]中
     * <p>
     * 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法的一个非常典型的应用。
     * 首先考虑下如何将2个有序数列合并。这个非常简单，只要从比较2个数列的第一个数，谁小就先取谁，
     * 取了后就在对应数列中删除这个数。然后再进行比较，如果有数列为空，那直接将另一个数列的数据依次取出即可。
     *
     * @param a
     * @param n
     * @param b
     * @param m
     * @param c
     */
    public static void mergeSort(int[] a, int n, int[] b, int m, int[] c) {
        int i, j, k;

        i = j = k = 0;
        while (i < n && j < m) {
            if (a[i] < b[j])
                c[k++] = a[i++];
            else
                c[k++] = b[j++];
        }

        while (i < n) {
            c[k++] = a[i++];
        }

        while (j < m) {
            c[k++] = b[j++];
        }
    }



}

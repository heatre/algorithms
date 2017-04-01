package com.swin.sorting;

import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**希尔排序(Shell Sort)：让数组中间隔为h的元素有序。这样的数组称为h数组。
 * shell sort是插入排序的一种改进，常用的希尔排序会使用(3^k-1)/2递增序列来去h值，从N/3递减到1
 * 算法性能：运行时间达不到平方级别
 * Created by Swin on 2016/12/22.
 */
public class ShellSort {
    //排序算法
    public static void sort(Comparable[] a) {
        int n = a.length;
        int h = 1;
        //使用(3^k-1)/2递增序列进行shell排序
        while (h < n / 3)
            h = 3 * h + 1;
        while (h >= 1) {
            //将数组变为h有序
            for (int i = h; i < n; ++i) {
                //将a[i]插入到a[i-h],a[i-2h]...中
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
                    exch(a, j, j - h);
                StdOut.println("h:"+h);
                show(a);
            }
            h /= 3;
        }
    }

    //比较v、w的大小，如果v小于w，返回true
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //交换数组a中i，j两个元素的位置
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    //单行打印数组
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    //测试数组是否有序
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }

    //测试用例
    public static void main(String[] args) {
        String[] a = Utils.getStringArray("tiny.txt");
        Stopwatch sw = new Stopwatch();
        sort(a);
        sw.elapsedTime();
        assert (isSorted(a));
        show(a);
    }
}

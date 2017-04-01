package com.swin.sorting;

import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by swin on 2017/4/1.
 */
public class BubbleSort {

    //排序算法
    public static void sort(Comparable[] a) {
        if (a.length <= 1)
            return;
        for(int i = a.length-1;i >0;i--)
            for(int j = 0;j<i;j++){
            if(!less(a[j],a[j+1]))
                exch(a,j,j+1);
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

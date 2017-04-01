package com.swin.sorting;

import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * 三切分快速排序(quick sort with 3-way partitioning):从左至右便利数组一次，维护一个指针lt使得
 * a[lo..lt-1]中的元素都小于v，一个指针gt使得a[gt+1..hi]中的元素都大于v,一个指针i使得a[lt..i-1]
 * 中的元素等于v，a[i..gt]中的元素未确定。
 * 算法性能：三切分的快速排序的运行时间和输入信息量的N倍成正比
 * Created by Swin on 2016/12/27.
 */
public class Quick3WaySort {
    //排序算法
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);//消除对输入的依赖
        show(a);
        sort(a, 0, a.length - 1);
    }


    //递归实现快速排序
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo)
            return;
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        Comparable v = a[lo];
        //遍历使a[lo..lt-1]<v=a[lt..gt]<a[gt+1..hi]
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0)
                exch(a, lt++, i++);
            else if (cmp > 0)
                exch(a, i, gt--);
            else
                i++;
            show(a);
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
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

package com.swin.sorting;

import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * 快速排序(quick sort)：快速排序是一种分治的排序算法。它将一个数组分成两个子数组，将两部分独立的排序。
 * 算法性能：长度为N的无重复元素的数组，快速排序平均需要~NlnN次比较和N/6的交换,最多需要~N^2/2次比较，
 * 但随机打乱数组可预防这种状况
 * 改进：1.切换到插入排序 2.三取样切分 3.熵最优的排序
 * Created by Swin on 2016/12/27.
 */
public class QuickSort {
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
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    //快速排序的切分,将数组切分为a[lo..i-1],a[i],a[i+1..hi]
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;//左分区索引
        int j = hi + 1;//右分区索引
        //选择第一个元素作为切分元素
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v))//从左向右扫描，a[i]<v时,i增大,保证i左侧的比v小，当
                if (i == hi)//向右扫描到尾部时结束
                    break;
            while (less(v, a[--j]))//从右向左扫描，v>a[j]时，j减少，保证j右侧的比v大
                if (j == lo)//向右扫描到开头时结束
                    break;
            if (i >= j)//指针相遇
                break;
            StdOut.println("a[i]:" + a[i] + " " + "a[j]:" + a[j]);
            //交换a[i],a[j]保证i左侧值不大于v，j右侧的值不小于v,此时a[j]<v&& a[i]>v，
            // 可保证a[i]<v,a[j]>v,即最终保证
            //切分点v左右两边有序，
            exch(a, i, j);
            show(a);
        }
        show(a);
        //当i==j时，指针相遇交换a[lo],a[j]切分结束，(切分值保留在a[j]当中)
        exch(a, lo, j);
        return j;
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

package com.swin.sorting;

import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**归并排序(merge sort):将两个有序的数组归并成一个更大的有序数组，其主要体现的了算法设计中的分治思想
 * 自底向上归并排序：归并微型数组，得到子数组，在归并子数组，得到排序好的数组，(一一归并，二二归并，四四归并，8，8归并 ...)
 * 算法性能：长度为N的任意数组，需要1/2NlgN~NlgN次比较，最多6NlgN次数组访问
 * Created by Swin on 2016/12/22.
 */
public class DownTopMergeSort {
    //归并排序辅助数组
    private static Comparable[] ax;

    //原地归并抽象方法,将a[lo..mid]和a[mid..hi]归并
    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        //将需要归并的两个数组复制到辅助数组；
        for (int k = lo; k <= hi; k++)
            ax[k] = a[k];
        //归并两个子数组
        for (int k = lo; k <= hi; k++) {
            if (i > mid)//i>mid表示左边子数组归并完成,直接复制右边子数组剩余的元素到a
                a[k] = ax[j++];
            else if (j > hi) //j>hi表示右边子数组归并完成,直接复制左边子数组剩余的元素到a
                a[k] = ax[i++];
                //
            else if (less(ax[j], ax[i]))//对比左右子数组当前位置的最小值，右子数组当前索引元素较小取当前值，索引后移
                a[k] = ax[j++];
            else//对比左右子数组当前位置的最小值，左子数组当前索引元素较小取当前值，索引后移
                a[k] = ax[i++];
        }
    }

    //排序算法
    public static void sort(Comparable[] a) {
        int n = a.length;

        ax = new Comparable[n];
        for(int sz = 1;sz<n;sz = sz+sz)//sz子数组大小
            for(int lo = 0;lo<n-sz;lo += sz+sz)//lo子数组索引
                merge(a,lo,lo+sz-1,Math.min(lo+sz+sz-1,n-1));
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

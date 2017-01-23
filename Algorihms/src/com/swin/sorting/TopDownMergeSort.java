package com.swin.sorting;

import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * 归并排序(merge sort):将两个有序的数组归并成一个更大的有序数组，其主要体现的了算法设计中的分治思想
 * 自顶向下归并排序：递归调用将两个子数组排序，在将两个子数组归并成一个数组，从而将整个数组排序
 * 算法性能：长度为N的任意数组，需要1/2NlgN~NlgN次比较，最多6NlgN次数组访问
 * 性能提升：1.小规模子数组使用插入排序 2.测试数组是否已经有序(a[mid]<a[mid+1]) 3.不将元素复制到辅助数组
 * Created by Swin on 2016/12/22.
 */
public class TopDownMergeSort {

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
         ax = new Comparable[a.length];
        sort(a,0,a.length-1);
    }

    //将数组分割为左右两部分数组
    private static void sort(Comparable[] a,int lo,int hi){
        if(hi<=lo)
            return;
        int mid = lo+(hi-lo)/2;//获取中间索引
        sort(a,lo,mid);//归并左半边
        sort(a,mid+1,hi);//归并右半边
        merge(a,lo,mid,hi);//归并结果
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

package com.swin.sorting;

import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * 插入排序(insertion sort):把待排序的元素按其关键码值的大小逐个插入到一个已经排好序的有序序列中，
 * 直到所有的元素插入完为止，得到一个新的有序序列
 * 算法性能：对于随机排列长度为N且主键不重复的数组，平均情况下需要~N^2/4次对比，~N^2/4次交换。
 * 最坏情况下需要~N^2/2次比较，~N^2/2次交换，最好情况下需要N-1次比较和0次交换
 * 时间复杂度：最差、平均都是O(N^2),最好O(N)
 * 空间复杂度：1
 * Created by Swin on 2016/12/22.
 */
public class InsertionSorting {

    //升序排列
    public static void sort(Comparable[] a) {
        int n = a.length;
        //从第二个元素开始，认为第一个元素为一个有序序列
        for (int i = 1; i < n; ++i) {
            //对于1到n-1中的每一个i，将a[i]与a[0]到a[i-1]的比它小的元素所有元素依次有序的交换。
            //在索引i从左到右变化过程中，i左侧的元素总是有序的，当i到达数组的右端时排序完成
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                StdOut.println("i:"+i+" j:"+j);
                show(a);
                exch(a, j, j - 1);
                show(a);
            }
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

package com.swin.sorting;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * 算法性能比较
 * Created by Swin on 2016/12/22.
 */
public class SortCompare {

    //计算排序插入、选择、希尔、归并、快速、堆排序对同一数组所用时间
    public static double time(String alg, Comparable[] a) {
        Stopwatch time = new Stopwatch();
        if (alg.equals("Insertion"))
            InsertionSorting.sort(a);
        if (alg.equals("Selection"))
            SelectionSort.sort(a);
        if (alg.equals("Shell"))
            ShellSort.sort(a);
        if (alg.equals("Merge"))
            ;
        if (alg.equals("Quick"))
            ;
        if (alg.equals("Heap"))
            ;
        return time.elapsedTime();
    }

    /**
     *
     * @param alg 算法名称
     * @param N   测试数据量
     * @param T   算法测试次数
     * @return    N个数据量测试T次所用的总时间
     */
    public static double timeRandomInput(String alg, int N, int T) {
        double total = 0.0;
        Double[] a = new Double[N];
        for (int t = 0; t < T; ++t) {
            for (int i = 0; i < N; ++i)
                a[i] = StdRandom.uniform();
            total+=time(alg,a);
        }
        return total;
    }

    public static void main(String[]args){
        String alg1 = "Insertion";
        String alg2 = "Selection";
        int N = 1000;
        int T= 100;
        double t1 = timeRandomInput(alg1,N,T);
        double t2 = timeRandomInput(alg2,N,T);
        StdOut.println(alg1+":"+t1);
        StdOut.println(alg2+":"+t2);
    }
}

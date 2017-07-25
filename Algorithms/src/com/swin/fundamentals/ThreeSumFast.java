package com.swin.fundamentals;

import com.swin.utils.Constant;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;

/**
 * problem:找出一个输入文件中所有和为0的整数对(3-sum)
 * 归并排序和二分查找实现线性对数级别(N^2*logN)解决3-sum问题
 * 算法思想：当且仅当-(a[i]+a[j])存在于数组当中,
 * Created by Swin on 2016/12/6.
 */
public class ThreeSumFast {
    //3-sum问题快速算法，所需时间为N^2*logN级别
    public int count(int a[]) {
        int cnt = 0;
        int n = a.length;
        Arrays.sort(a);
        for (int i = 0; i < n; i++)
            for (int j = i+1; j < n; j++) {
                if (Arrays.binarySearch(a, -(a[i] + a[j])) > j) {
                    cnt++;
                    StdOut.println("cnt:"+cnt);
                    StdOut.println("a[i]:" + a[i]+" i:"+i);
                    StdOut.println("a[j]:" + a[j]+" j:"+j);
                }
            }

        return cnt;
    }

    //暴力算法的3-sum问题，耗时：N^3级别
    public int count2(int a[]) {
        int cnt = 0;
        for (int i = 0; i < a.length; ++i)
            for (int j = 0; j < a.length; j++)
                for (int k = 0; k < a.length; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        cnt++;
//                        StdOut.println("a[i]:" + a[i]);
//                        StdOut.println("a[j]:" + a[j]);
//                        StdOut.println("a[k]:" + a[k]);
                    }
                }
        return cnt/2;
    }

    public static void main(String[] args) {
        int[] a = Utils.getIntArray("1Kints.txt");

        ThreeSumFast tsf = new ThreeSumFast();

        Stopwatch t = new Stopwatch();
        int cnt = tsf.count(a);
        StdOut.println("time:" + t.elapsedTime());
        StdOut.println("cnt:" + cnt);

//        Stopwatch t2 = new Stopwatch();
//        int cnt2 = tsf.count2(a);
//        StdOut.println("time:" + t2.elapsedTime());
//        StdOut.println("cnt2:" + cnt2);
    }
}

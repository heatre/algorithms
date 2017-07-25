package com.swin.fundamentals;


import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;

/**
 * problem:找出一个输入文件中所有和为0的整数对(2-sum)
 * 归并排序和二分查找实现线性对数级别(NlogN)解决2-sum问题
 * 算法思想：当且仅当-a[i]存在于数组当中(a[i]不等于0时),a[i]存在于某个和为零的整数对中
 * Created by Swin on 2016/12/6.
 */
public class TwoSumFast {
    //计算和为0的整数对的数目
    public int count(int[] a) {
        //对数组a进行归并排序
        Arrays.sort(a);
        int n = a.length;
        int cnt = 0;
        for (int i = 0; i < n; ++i) {
            //当数组a中存在与a[i]和为0的-a[i]时cnt加1,
            if (Arrays.binarySearch(a, -a[i]) > i) {
                StdOut.println("a[i]:" + a[i]);
                cnt++;
            }
        }
        return cnt;
    }

    //暴力法2-sum问题
    public int count2(int a[]) {
        int cnt = 0;
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a.length; j++) {
                if (a[i] + a[j] == 0) {
//                    StdOut.println("a[i]:" + a[i]);
                    cnt++;
                }
            }
        return cnt / 2;//重复计算一次
    }

    public static void main(String[] args) {

        TwoSumFast tsf = new TwoSumFast();
        int[] a = Utils.getIntArray("1Kints.txt");
        Stopwatch t = new Stopwatch();
        int cnt = tsf.count(a);
        int cnt2 = tsf.count2(a);
        StdOut.println("time:" + t.elapsedTime());
        StdOut.println(cnt);
    }
}

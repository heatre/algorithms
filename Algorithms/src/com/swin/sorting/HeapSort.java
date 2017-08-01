package com.swin.sorting;

import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;

/**
 * 二叉堆：是一组能够用堆有序的完全二叉树排序的元素，并在数组中按照层级存储（不使用数组的打一个位置）
 * 在一个堆中，位置k的节点的父节点的位置为|_k/2_|，而他的直接点位置为2k，2k+1，我们可以通过数组的索引
 * 在树中上下移动：从a[k]向上一层就令k等于k/2，向下一层这令k等于2k或者2k+1
 * 堆排序：1.构造二叉堆 2.下称排序
 * Created by Swin on 2016/12/29.
 */
public class HeapSort {

  //  private Comparable[] a;//优先队列数组存储结构
    int N = 0;      //优先队列当前元素数


    /**
     * 比较队列中两个元素的大小
     * @param i 第一元素
     * @param j 第二元素
     * @return a[i]>a[j]返回false，小于返回true，等于返回false
     */
    private static boolean less(Comparable[] a,int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    /**
     * 交换队列中两个元素位置
     * @param i 第一个元素位置
     * @param j 第二个元素位置
     */
    private static void exch(Comparable[] a,int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * 队列是否为空
     * @return 空返回true，否则返回false
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * 优先队列中元素个数
     * @return 元素数
     */
    public int size() {
        return N;
    }

    public static void show(Comparable[] a){
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    /**
     * 由上至下的堆有序化（下沉）
     * @param k 下沉元素位置索引
     */
    public static void sink(Comparable[] a,int k,int n){
        while (2*k<=n){
            int j = 2*k;
            if(j<n && less(a,j,j+1))
                j++;
            if(less(a,j,k))
                break;
            exch(a,k,j);
            k = j;
        }
    }

    /**
     * 进行堆排序
     * @param a 待排序数组
     */
    public static void sort(Comparable[]a){
        int n = a.length-1;//数组a[0]位置不使用
        for(int k = n/2;k>=1;k--){//构造二叉堆
            sink(a,k,n);
        }

        while(n>1){
            exch(a,1,n--);//将最大元素和最后一个元素交换
            sink(a,1,n);  //恢复堆有序，获取最大元素
        }
    }

    public static void main(String[] args){
        String[] a = Utils.getStringArray("tiny.txt");
        String[] a1 = new String[a.length+1];
        for(int i = 0;i<a.length;i++){
            a1[i+1] = a[i];
        }
        HeapSort.sort(a1);
        HeapSort.show(a1);

    }
}

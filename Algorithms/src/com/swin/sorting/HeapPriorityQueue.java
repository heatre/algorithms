package com.swin.sorting;

import com.swin.utils.Utils;
import edu.princeton.cs.algs4.Heap;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * 二叉堆：是一组能够用堆有序的完全二叉树排序的元素，并在数组中按照层级存储（不使用数组的打一个位置）
 * 在一个堆中，位置k的节点的父节点的位置为|_k/2_|，而他的直接点位置为2k，2k+1，我们可以通过数组的索引
 * 在树中上下移动：从a[k]向上一层就令k等于k/2，向下一层这令k等于2k或者2k+1
 * 命题：一棵大小为N的完全二叉树的高度为|_lgN_|
 * 基于堆的优先队列：含有N个元素的优先队列，插入元素操作只需要不超过lgN+1次比较，删除最大元素操作需要不超过2lgN次比较
 * Created by Swin on 2016/12/29.
 */
public class HeapPriorityQueue<Key extends Comparable<Key>> {
    private Key[] a;//优先队列数组存储结构
    int N = 0;      //优先队列当前元素数

    /**
     * 优先队列构造函数
     * @param max 优先队列最大含有的函数技术
     */
    public HeapPriorityQueue(int max) {
        a = (Key[]) new Comparable[max+1];//位置a[0]不使用
    }

    public HeapPriorityQueue(Key[] a){
        this.a = (Key[]) new Comparable[a.length];
    }
    /**
     * 比较队列中两个元素的大小
     * @param i 第一元素
     * @param j 第二元素
     * @return a[i]>a[j]返回false，小于返回true，等于返回false
     */
    private boolean less(int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    /**
     * 交换队列中两个元素位置
     * @param i 第一个元素位置
     * @param j 第二个元素位置
     */
    private void exch(int i, int j) {
        Key t = a[i];
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

    public void show(){
        for (int i = 0; i <= N; i++) {
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    /**
     * 由下至上的堆有序化（上浮）
     * @param k 上浮元素位置索引
     */
    private void swin(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    /**
     * 由上至下的堆有序化（下沉）
     * @param k 下沉元素位置索引
     */
    private void sink(int k){
        while (2*k<=N){
            int j = 2*k;
            if(j<N && less(j,j+1))
                j++;
            if(less(j,k))
                break;
            exch(k,j);
            k = j;
        }
    }

    /**
     * 向队列中插入元素
     * @param k 待插入的元素
     */
    public void insert(Key k){
        a[++N] = k;
        swin(N);
    }

    /**
     * 删除队列中最大元素
     * @return 返回删除的元素
     */
    public Key delMax(){
        Key key = a[1];//a[1]为二叉堆根元素最大
        exch(1,N--);//将最后一个元素和根元素交换
        a[N+1] = null; //防止对象游离
        sink(1); //恢复堆有序性
        return key;
    }

    public static void main(String[] args){
        String[] a = Utils.getStringArray("tiny.txt");
        HeapPriorityQueue<String> hpq = new HeapPriorityQueue<>(a.length+1);
        for(int i = 0;i<a.length;++i){
            hpq.insert(a[i]);
        }
        while(!hpq.isEmpty()){
            StdOut.print(hpq.delMax()+" ");
        }


    }
}

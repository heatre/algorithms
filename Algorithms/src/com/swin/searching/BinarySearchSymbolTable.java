package com.swin.searching;

import com.swin.fundamentals.LinkQueue;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;

/**
 * 二分查找(binary search),基于有序数组的符号表
 * 命题B：在包含N个键的有序数组中进行二分查找最多需要(lgN+1)次比较（无论成功与否）
 * 向大小为N的有序数组中插入一个新的元素最坏情况下需要访问~2N次数组，一次向一个空的
 * 符号表中插入N个元素的最欢情况下需要~N^2次数组访问
 */
public class BinarySearchSymbolTable<Key extends Comparable<Key>, Val> {
    private Key[] keys;//存储键数组
    private Val[] vals;//存储值数组
    private int N;    //符号表中键值对数

    /**
     * 二分查找构造函数
     *
     * @param capacity 符号表容量
     */
    public BinarySearchSymbolTable(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Val[]) new Object[capacity];
        N = 0;
    }

    /**
     * 符号表键值对数
     *
     * @return 键值对数
     */
    public int size() {
        return N;
    }

    /**
     * 重新分配符号表容量
     * @param capacity 容量
     */
    private void resize(int capacity) {
        assert capacity >= N;
        Key[]   tempk = (Key[])   new Comparable[capacity];
        Val[] tempv = (Val[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }


    /**
     * 符号表是否为空
     *
     * @return 为空返回true，否则返回true
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 符号表中是否包含键值k
     *
     * @param k 键
     * @return
     */
    public boolean contains(Key k) {
        int i = rank(k);
        return i < N;
    }

    /**
     * 小于k的键的数量
     *
     * @param k 比较的键
     * @return 如果表中存在该键，返回该键位置(索引），也就表示表中小于它的键的数量
     * 如果表中不存在该键，也返回表中小于它的键的数量
     */
    public int rank(Key k) {
        int lo = 0;
        int hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = k.compareTo(keys[mid]);
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return lo;//二分查找不管是否找到k，lo都会一直向右移动
    }

    /**
     * 获取键k对应的值
     *
     * @param k 键k
     * @return 若符号表为空，或者键k不存在返回null
     */
    public Val get(Key k) {
        if(k == null)
            throw new NullPointerException("argument is null");
        if (isEmpty())
            return null;//符号表为空返回null

        int i = rank(k);//获取键k位置，如果键k与keys[k]相等返回其值
        if (i < N && k.compareTo(keys[i]) == 0)
            return vals[i];
        else
            return null;
    }

    /**
     * 将键值对存入表中，若值为空则删除k
     *
     * @param k 键
     * @param v 值
     */
    public void put(Key k, Val v) {
        int i = rank(k);
        if (i < N && k.compareTo(keys[i]) == 0) {//如果k存在符号表中更新其值
            vals[i] = v;
            return;
        }

        //只为空则删除符号表中对应的键和值
        if (v == null) {
            delete(k);
            return;
        }

        //符号表已满，扩大容量
        if(N == keys.length)
            resize(2*keys.length);
        for (int j = N; j > i; j--) {//如果k不在符号表中则在i位置插入k
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = k;//插入键
        vals[i] = v;//插入值
        N++;
    }

    /**
     * 最小键
     *
     * @return 最小键
     */
    public Key min() {
        if (isEmpty())
            return null;
        return keys[0];
    }

    /**
     * 最大键
     *
     * @return 最大键
     */
    public Key max() {
        if (isEmpty())
            return null;
        return keys[N - 1];
    }

    /**
     * 排名为k的键
     *
     * @param k 排名
     * @return 排名为k的键
     */
    public Key select(int k) {
        if (k < 0 || k >= N)
            return null;
        return keys[k];
    }

    /**
     * 小于等于k的最大键
     *
     * @param k 键
     * @return 小于等于k的最大键
     */
    public Key floor(Key k) {
        if (k == null)
            throw new NullPointerException("argument floor() is null");
        int i = rank(k);
        if (i < N && k.compareTo(keys[i]) == 0)
            return keys[i];
        else if (i == 0)
            return null;
        else
            return keys[i - 1];

    }

    /**
     * 大于等于k的最小值
     *
     * @param k 键
     * @return 大于等于k最小键
     */
    public Key ceiling(Key k) {
        if (k == null)
            throw new NullPointerException("argument ceiling() is null");
        int i = rank(k);
        if (i == N)
            return null;
        return keys[i];
    }

    /**
     * 从表中删除键值及对应的值
     *
     * @param k 待删除的键
     */
    public void delete(Key k) {
        if (k == null)
            throw new NullPointerException("argument delete() is null");
        if (isEmpty())
            return;
        int i = rank(k);

        if (i == N || k.compareTo(keys[i]) != 0)//键不在表中
            return;

        for (int j = i; j < N - 1; j++) {//键在表中
            keys[j] = keys[j + 1];
            vals[j] = vals[j + 1];
        }

        N--;
        keys[N] = null;//防止对象游离
        keys[N] = null;//防止对象游离
    }

    /**
     * 删除最小键
     */
    public void deleteMin(){
        if(isEmpty())
            return;
        delete(min());
    }

    /**
     * 删除最大键
     */
    public void deleteMax(){
        if(isEmpty())
            return;
        delete(max());
    }

    /**
     * lo..hi之间键的数量
     * @param lo 低键
     * @param hi 高键
     * @return lo..hi键数量
     */
    public int size(Key lo,Key hi){
        if(lo == null)
            throw new NullPointerException("first argument is null");
        if(hi == null)
            throw new NullPointerException("second argument is null");
        if(lo.compareTo(hi)>0)//lo大于hi时则返回0
            return 0;
        if(contains(hi))
            return rank(hi)-rank(lo)+1;
        else
            return rank(hi)-rank(lo);

    }

    /**
     * 根据给定键值范围，给出符号表键值范围内的键值集合
     * @param lo 低位键
     * @param hi 高位键
     * @return 范围内键集合
     */
    public Iterable<Key> keys(Key lo,Key hi){
        if(lo == null)
            throw new NullPointerException("first argument is null");
        if(hi == null)
            throw new NullPointerException("second argument is null");
        LinkQueue<Key> queue = new LinkQueue<>();
        if(lo.compareTo(hi)>0)
            return queue;
        for(int i = rank(lo);i<rank(hi);i++)
            queue.enqueue(keys[i]);
        if(contains(hi))
            queue.enqueue(keys[rank(hi)]);
        return queue;
    }

    /**
     * 给出符号表中键值集合
     * @return 键值集合
     */
    public Iterable<Key> keys(){
        return keys(min(),max());
    }

    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++)
            if (keys[i].compareTo(keys[i-1]) < 0) return false;
        return true;
    }

    // check that rank(select(i)) = i
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (int i = 0; i < size(); i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) return false;
        return true;
    }

    /**
     * 测试用例
     * @param args
     */
    public static void main(String[] args){
        String [] a = Utils.getStringArray("tiny.txt");
        BinarySearchSymbolTable<String,Integer> ss = new BinarySearchSymbolTable<>(100);
        for(int i=0;i<a.length;i++){
            ss.put(a[i],i);
        }

        for(String s:ss.keys()){
            StdOut.println(s+" "+ss.get(s));
        }
    }

}

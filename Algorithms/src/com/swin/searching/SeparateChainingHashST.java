package com.swin.searching;

import com.swin.fundamentals.LinkQueue;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * 基于拉链法的散列表(hash wiht separate chaining)
 * 拉链法：将大小为M的数组中的每个元素指向一条链表，链表中的每个结点都存储
 * 了散列值为该元素索引的键值对
 * 假设(均匀散列假设)：我们使用的散列函数能够均匀并独立地将所有散列的键散布在0到M-1之间
 * 命题K：在一张含有M条链表和N个键的散列表中，任意一条链表中的键的数量均在N/M的常数因子
 * 范围的概率无线趋近于1。
 * 性质L：在含有M条链表和N个键的散列表中，未命中查找和 插入操作所需的比较次数为~N/M
 */
public class SeparateChainingHashST<Key, Val> {
    private static final int INIT_CAPACITY = 4;//散列表默认容量，即默认M大小
    private int n;//散列包键值对数
    private int m;//散列表大小
    private SequentialSearchST<Key, Val>[] st;//存放链表对象的数组

    /**
     * 默认构造函数，构造默认大小的散列表
     */
    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * 构造函数，根据容量大小构造散列表
     *
     * @param cap 散列表大小
     */
    public SeparateChainingHashST(int cap) {
        this.m = cap;
        this.n = 0;
        st = new SequentialSearchST[cap];
        for (int i = 0; i < m; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    /**
     * 返回散列表大小
     *
     * @return 散列表大小
     */
    public int size() {
        return n;
    }

    /**
     * 当前键是否在散列表中
     *
     * @param k 键
     * @return 是返回true，否则返回false
     */
    public boolean contains(Key k) {
        if (k == null)
            throw new NullPointerException("first argument is null");
        return get(k) != null;
    }

    /**
     * 散列表是否分为空
     *
     * @return 空返回true，否则返回true
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 重新分配散列表大小
     *
     * @param cap 新的散列表容量
     */
    private void resize(int cap) {
        SeparateChainingHashST<Key, Val> temp = new SeparateChainingHashST(cap);
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.st = temp.st;
    }

    /**
     * 散列函数
     * @param key 键值
     */
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /**
     * 根据键值从散列表中获取值
     * @param key 键
     * @return 键对应的值
     */
    public Val get(Key key){
        if(key == null)
            throw new NullPointerException("argument is null");
        return st[hash(key)].get(key);
    }

    /**
     *
     * @param key
     * @param val
     */
    public void put(Key key,Val val){
        if(key == null)
            throw new NullPointerException("first argument is null");
        if(val == null) {
            delete(key);
            return;
        }

        //如果键值对数大于数组容量的10倍，扩展散列表链表数为两倍
        if(n>10*m)
            resize(2*m);

        int i = hash(key);
        if(!contains(key))
            n++;
        st[i].put(key,val);
    }

    /**
     * 删除键对应的结点
     * @param key 键
     */
    public void delete(Key key){
        if(key == null)
            throw new NullPointerException("argument is null");
        if(isEmpty())
           return;
        int i = hash(key);

        if(contains(key))
            n--;
        st[i].delete(key);

        //如果链表的平均长度小于2，则将散列表大小减少一半
        if(m>INIT_CAPACITY && n<=2*m)
            resize(m/2);

    }

    /**
     * 返回散列表中键集合
     * @return
     */
    public Iterable<Key> keys() {
        LinkQueue<Key> queue = new LinkQueue<>();
        for(int i = 0;i<m;i++){
            for(Key key:st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    }

    /**
     * 测试用例
     * @param args
     */
    public static void main(String[] args){
        String[] a = Utils.getStringArray("tiny.txt");
        SeparateChainingHashST<String, Integer> ss
                = new SeparateChainingHashST<>();
        for (int i = 0; i < a.length; i++) {
            ss.put(a[i], i);
        }

        for (String s : ss.keys()) {
            StdOut.println(s + " " + ss.get(s));
        }

        for(int i = 0;i<a.length-3;i++){
            ss.delete(a[i]);
        }

        for (String s : ss.keys()) {
            StdOut.println(s + " " + ss.get(s));
        }


    }
}

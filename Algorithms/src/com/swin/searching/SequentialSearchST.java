package com.swin.searching;

import com.swin.fundamentals.LinkQueue;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;

/**
 * 顺序查找(Sequential search)，基于无序链表.
 * 查找的成本模型：一般统计比较的次数，内循环不进行比较的情况下则统计数组的访问次数
 * 命题A：在含有N对键值的基于无序链表的符号表中，未命中查找和插入操作都需要N比较。
 * 命中查找最坏情况下需要N次比较。向一个空的无序符号表中插入N个不同的键需要~N^2次比较。
 * 推论：向一个空表中插入N个不同的键需要~N^2次比较
 * Created by swin on 2017/8/3.
 */
public class SequentialSearchST<Key, Val> {
    private Node first; //键值对节点
    private int n;//键值对数

    /**
     * 无序符号表存储节点
     */
    private class Node {
        Key key; //键
        Val val; //值
        Node next;

        public Node(Key k, Val v, Node next) {
            key = k;
            val = v;
            this.next = next;
        }
    }

    /**
     * 构造函数
     */
    public SequentialSearchST() {
        first = null;
        n = 0;
    }

    /**
     * 键值对数
     *
     * @return 键值对数量
     */
    public int size() {
        return n;
    }

    /**
     * 符号表是否为空
     *
     * @return 空返回true，否则返回false
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 符号表中是否包含该键
     *
     * @param k 键
     * @return 包含返回true，否则false
     */
    public boolean contains(Key k) {
        if (k == null)
            throw new NullPointerException("argument contains() is null");
        return get(k) != null;
    }


    /**
     * 根据键顺序查找值
     *
     * @param k 键值
     * @return 命中查找返回值，未命中返回null
     */
    public Val get(Key k) {
        if (k == null)
            throw new NullPointerException("argument get() is null");
        for (Node n = first; n != null; n = n.next)//顺序遍历查找
            if (k.equals(n.key))
                return n.val;//命中查找
        return null;//未命中查找
    }

    /**
     * 将键值插入符号表，查找给定的键，找到更新其值，否则在表中新建节点
     *
     * @param k 键
     * @param v 值
     */
    public void put(Key k, Val v) {
        if (k == null)
            throw new NullPointerException("argument put() is null");
        for (Node n = first; n != null; n = n.next)
            if (k.equals(n.key)) {
                n.val = v;//命中查找，更新键对应的值
                return;
            }
        first = new Node(k, v, first);//未命中，插入节点
        n++;
    }

    /**
     * 根据键删除符号表中对应的值
     *
     * @param k 键
     */
    public void delete(Key k) {
        if (k == null)
            throw new NullPointerException("argument delete() is null");
        first = delete(first, k);
    }

    /**
     * 递归调用删除
     *
     * @param nd 当前递归到节点
     * @param k  待删除出键
     * @return 下一个元素
     */
    private Node delete(Node nd, Key k) {
        if (nd == null)
            return null;
        if (k.equals(nd.key)) {
            n--;
            return nd.next;
        }
        nd.next = delete(nd.next, k);
        return nd;
    }

    /**
     * 无序符号表键值集合
     *
     * @return 键值集合
     */
    public Iterable<Key> keys() {
        LinkQueue<Key> queue = new LinkQueue<Key>();
        for (Node nd = first; nd != null; nd = nd.next)
            queue.enqueue(nd.key);
        return queue;
    }

    /**
     * 测试用例
     *
     * @param args main参数
     */
    public static void main(String[] args) {
        String[] a = Utils.getStringArray("tiny.txt");
        SequentialSearchST<String, Integer> ss = new SequentialSearchST<>();
        for (int i = 0; i < a.length; i++) {
            ss.put(a[i], i);
        }

        for (String s : ss.keys()) {
            StdOut.println(s + " " + ss.get(s));
        }
    }
}

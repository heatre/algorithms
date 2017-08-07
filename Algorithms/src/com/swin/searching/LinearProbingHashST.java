package com.swin.searching;

import com.swin.fundamentals.LinkQueue;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;

/**
 * 基于线性探测的符号表(hash with linear probing)
 * 开放地址散列表：用大小为M的数组保存N个键值对，其中M>N,依靠数组中的空位解决碰撞冲突,
 * 基于这种策略的所有方法被统称为开放地址散列表。开放地址散列表中最简单的方法角线性探测法。
 * 线性探测法：当碰撞发生时(一个键的散列值已经被另一个不同的键占用了,我们直接检查三表中
 * 下一个位置(将索引加1),这样线性探测产生三种结果：1.命中，该位置键和别查找的键相同;
 * 2.未命中，键为空（该位置没有键);3.继续查找，该位置的键和别查找的键不同
 */
public class LinearProbingHashST<Key, Val> {

    private static final int INIT_CAPACITY = 4;//散列表默认大小
    private int n;//散列表中键值对数
    private int m;//散列表大小
    private Key[] keys;//键数组
    private Val[] vals;//值数组

    /**
     * 默认构造函数，构造默认大小的散列表
     */
    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * 构造函数，根据容量大小构造散列表
     *
     * @param cap 散列表大小
     */
    public LinearProbingHashST(int cap) {
        this.m = cap;
        this.n = 0;
        keys = (Key[]) new Object[cap];
        vals = (Val[]) new Object[cap];
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
        LinearProbingHashST<Key, Val> temp = new LinearProbingHashST<>(cap);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        this.m = temp.m;
        this.vals = temp.vals;
        this.keys = temp.keys;
    }

    /**
     * 散列函数
     *
     * @param key 键值
     */
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /**
     * 根据键值从散列表中获取值
     *
     * @param key 键
     * @return 键对应的值
     */
    public Val get(Key key) {
        if (key == null)
            throw new NullPointerException("argument is null");
        int t = hash(key);
        for (int i = t; keys[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }

    /**
     * @param key
     * @param val
     */
    public void put(Key key, Val val) {
        if (key == null)
            throw new NullPointerException("first argument is null");
        if (val == null) {
            delete(key);
            return;
        }

        //如果键值对数大于散列表大小将散列表加倍
        if (n > m / 2)
            resize(2 * m);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m)
            if (key.equals(keys[i])) {
                vals[i] = val;
                return;
            }
        //此时keys[i] == null
        keys[i] = key;
        vals[i] = val;
        n++;
    }

    /**
     * 删除键对应的结点
     *
     * @param key 键
     */
    public void delete(Key key) {
        if (key == null)
            throw new NullPointerException("argument is null");
        if (!contains(key))
            return;

        //找到key的位置
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // 删除键和相关的值
        keys[i] = null;
        vals[i] = null;

        // 重新散列表
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key keyToRehash = keys[i];
            Val valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m / 8) resize(m / 2);
        ;

    }

    /**
     * 返回散列表中键集合
     *
     * @return
     */
    public Iterable<Key> keys() {
        LinkQueue<Key> queue = new LinkQueue<>();
        for (int i = 0; i < m; i++) {
            if (keys[i] != null)
                queue.enqueue(keys[i]);
        }
        return queue;
    }

    /**
     * 测试用例
     *
     * @param args
     */
    public static void main(String[] args) {
        String[] a = Utils.getStringArray("tiny.txt");
        LinearProbingHashST<String, Integer> ss
                = new LinearProbingHashST<>();
        for (int i = 0; i < a.length; i++) {
            ss.put(a[i], i);
        }

        for (String s : ss.keys()) {
            StdOut.println(s + " " + ss.get(s));
        }

        for (int i = 0; i < a.length - 3; i++) {
            ss.delete(a[i]);
        }

        for (String s : ss.keys()) {
            StdOut.println(s + " " + ss.get(s));
        }


    }
}

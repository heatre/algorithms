package com.swin.searching;

import com.swin.fundamentals.LinkQueue;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * 二叉查找树(binary search tree):其中每个节点的键都大于其左子树中任意节点的键
 * 而小于右子树的任意节点的键
 * 命题C：由N个随机键构造的二叉查找树中，查找命中平均需要~2lnN比较，插入操作和查找
 * 未命中平均需要~2lnN次比较
 * 命题E:在一棵二叉查找树中，所有操作最坏情况下需要时间都和树的高度成正比
 *
 */
public class BinarySearchTree<Key extends Comparable<Key>, Val> {
    private Node root;//bst根节点

    /**
     * 私有内部类表示bst节点
     */
    private class Node {
        Key key;//键
        Val val;//值
        Node left;//指向左子树链接
        Node right;//指向右子树链接
        int n;//以给节点为根的子树中节点总数

        public Node(Key k, Val v, int n) {
            this.key = k;
            this.val = v;
            this.n = n;
        }
    }

    /**
     * 二叉查找树构造函数
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * 二叉树中键值对数
     *
     * @return 键值对数
     */
    public int size() {
        return size(root);
    }

    /**
     * 以该节点为根的子树中节点总数
     *
     * @param node 当前节点
     * @return 子树中节点总数
     */
    public int size(Node node) {
        if (node == null)
            return 0;
        return node.n;
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
        return get(k) != null;
    }


    /**
     * 获取键k对应的值
     *
     * @param k 键k
     * @return 若符号表为空，或者键k不存在返回null
     */
    public Val get(Key k) {
        if (k == null)
            throw new NullPointerException("argument is null");
        return get(root, k);
    }

    /**
     * 以该节点为根的bst中键k对应的值
     * 递归算法：如果树为空则未命中；如果查找的键和根节点相等查找命中，否则
     * 递归地在子树中查找，键较小就选择左子树，较大选择右子树
     *
     * @param node bst中某个节点
     * @param k    查找的键
     * @return 值
     */
    public Val get(Node node, Key k) {
        if (node == null)
            return null;
        int cmp = k.compareTo(node.key);
        if (cmp < 0)
            return get(node.left, k);
        else if (cmp > 0)
            return get(node.right, k);
        else
            return node.val;
    }

    /**
     * 将键值对存入bst中，若值为空则删除k
     *
     * @param k 键
     * @param v 值
     */
    public void put(Key k, Val v) {
        if (k == null)
            throw new NullPointerException("first argument is null");
        root = put(root, k, v);
    }

    /**
     * bst插入键值对
     * 递归算法：如果树是空的，就返回一个含有该键值对的新的节点；如果查找的键小于
     * 更节点的键，在左子树中插入该键，否则在右子树中插入该键
     *
     * @param node 节点
     * @param k    键
     * @param v    值
     */
    public Node put(Node node, Key k, Val v) {
        if (node == null)
            return new Node(k, v, 1);
        int cmp = k.compareTo(node.key);
        if (cmp < 0)
            node.left = put(node.left, k, v);
        else if (cmp > 0)
            node.right = put(node.right, k, v);
        else
            node.val = v;
        node.n = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * 最小键
     *
     * @return 最小键
     */
    public Key min() {
        if (isEmpty())
            throw new NoSuchElementException("symbol table is empty");
        return min(root).key;
    }

    /**
     * 递归查找key最小节点
     *
     * @param node 节点
     * @return 最小节点
     */
    public Node min(Node node) {
        if (node.left == null)//左链接为空则该节点就是key最小节点
            return node;
        return min(node.left);
    }

    /**
     * 最大键
     *
     * @return 最大键
     */
    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("symbol table is empty");
        return max(root).key;
    }

    /**
     * 递归查找key最大节点
     *
     * @param node 根节点
     * @return 最大节点
     */
    public Node max(Node node) {
        if (node.right == null)//右链接为空则该节点为key最大节点
            return node;
        return max(node.right);
    }

    /**
     * 排名为k的键
     *
     * @param k 排名
     * @return 排名为k的键
     */
    public Key select(int k) {
        if (k < 0 || k >= size())
            throw new IllegalArgumentException("argument is beyond");
        return select(root, k).key;
    }

    /**
     * 排名为k的节点
     *
     * @param node 根节点
     * @param k    排名
     * @return 排名为k的节点
     */
    public Node select(Node node, int k) {
        if (node == null)
            return null;
        int t = size(node.left);
        if (t > k)
            return select(node.left, k);
        else if (t < k)
            return select(node.right, k - t - 1);
        else
            return node;
    }

    /**
     * 小于k的键的数量
     *
     * @param k 比较的键
     * @return 该键的排名
     */
    public int rank(Key k) {
        if (k == null)
            throw new IllegalArgumentException("argument is null");
        return rank(root, k);
    }

    /**
     * 返回以node为根节点的子树中小于node.key的键的数量
     *
     * @param node 当前根节点
     * @return 小于key的节点数量
     */
    public int rank(Node node, Key k) {
        if (node == null)
            return 0;
        int cmp = k.compareTo(node.key);
        if (cmp < 0)
            return rank(node.left, k);
        else if (cmp > 0)
            return 1 + size(node.left) + rank(node.right, k);
        else
            return size(node.left);
    }

    /**
     * 小于等于k的最大键
     *
     * @param k 键
     * @return 小于等于k的最大键
     */
    public Key floor(Key k) {
        if (k == null)
            throw new NullPointerException("first argument is null");
        if (isEmpty())
            throw new NoSuchElementException("symbol table is empty");
        Node node = floor(root, k);
        if (node != null)
            return node.key;
        return null;
    }

    /**
     * 返回小于等于key最大节点
     *
     * @param node 根节点
     * @param k    键
     * @return 子树中小于等于k最大节点
     */
    public Node floor(Node node, Key k) {
        if (node == null)
            return null;
        int cmp = k.compareTo(node.key);
        if (cmp < 0)
            return floor(node.left, k);//递归左子树
        if (cmp == 0)
            return node; //命中查找，直接返回

        Node t = floor(node.right, k);//递归右子树key是否存在
        if (t != null)
            return t;
        else return node;
    }

    /**
     * 大于等于k的最小值
     *
     * @param k 键
     * @return 大于等于k最小键
     */
    public Key ceiling(Key k) {
        if (k == null)
            throw new NullPointerException("first argument is null");
        if (isEmpty())
            throw new NoSuchElementException("symbol table is empty");
        Node node = ceiling(root, k);
        if (node != null)
            return node.key;
        return null;
    }

    /**
     * 返回大于等于key最大节点
     *
     * @param node 根节点
     * @param k    键
     * @return 子树中大于等于k最大节点
     */
    public Node ceiling(Node node, Key k) {
        if (node == null)
            return null;
        int cmp = k.compareTo(node.key);
        if (cmp > 0)
            return floor(node.right, k);//递归左子树
        if (cmp == 0)
            return node; //命中查找，直接返回

        Node t = floor(node.left, k);//递归右子树key是否存在
        if (t != null)
            return t;
        else return node;
    }


    /**
     * 从表中删除键值及对应的值
     *
     * @param k 待删除的键
     */
    public void delete(Key k) {
        if(k == null)
            throw new IllegalArgumentException("first argument is null");
        if(isEmpty())
            throw new NoSuchElementException("symbol table is null");
        root  = delete(root,k);
    }

    public Node delete(Node node,Key k){
        if(node == null)
            return null;
        int cmp = k.compareTo(node.key);
        if(cmp<0)
            node.left = delete(node.left,k);
        else if(cmp >0)
            node.right = delete(node.right,k);
        else {
            if(node.right == null)
                return node.left;
            if(node.left == null)
                return node.right;
            Node t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        }

        node.n = size(node.left)+size(node.right)+1;
        return node;
    }

    /**
     * 删除最小键
     */
    public void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("symbol table is empty");
        root = deleteMin(root);
    }

    public Node deleteMin(Node node) {
        if (node.left == null)
            return node.right;
        node.left = deleteMin(node.left);
        node.n = size(node.left) + size(node.right) + 1;
        return node;
    }

    /**
     * 删除最大键
     */
    public void deleteMax() {
        if (isEmpty())
            throw new NoSuchElementException("symbol table is empty");
        root = deleteMax(root);
    }

    public Node deleteMax(Node node){
        if(node.right == null)
            return node.left;
        node.right = deleteMax(node.right);
        node.n = size(node.left)+size(node.right)+1;
        return node;
    }

    /**
     * lo..hi之间键的数量
     *
     * @param lo 低键
     * @param hi 高键
     * @return lo..hi键数量
     */
    public int size(Key lo, Key hi) {
        if (lo == null)
            throw new NullPointerException("first argument is null");
        if (hi == null)
            throw new NullPointerException("second argument is null");
        if (lo.compareTo(hi) > 0)//lo大于hi时则返回0
            return 0;
        if (contains(hi))
            return rank(hi) - rank(lo) + 1;
        else
            return rank(hi) - rank(lo);

    }

    /**
     * 根据给定键值范围，给出符号表键值范围内的键值集合
     *
     * @param lo 低位键
     * @param hi 高位键
     * @return 范围内键集合
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new NullPointerException("first argument to keys() is null");
        if (hi == null) throw new NullPointerException("second argument to keys() is null");

        LinkQueue<Key> queue = new LinkQueue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, LinkQueue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    /**
     * 给出符号表中键值集合
     *
     * @return 键值集合
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    private boolean check() {
        if (!isBST()) StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.n != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    /**
     * 测试用例
     *
     * @param args
     */
    public static void main(String[] args) {
        String[] a = Utils.getStringArray("tiny.txt");
        BinarySearchTree<String, Integer> ss = new BinarySearchTree<>();
        for (int i = 0; i < a.length; i++) {
            ss.put(a[i], i);
        }

        for (String s : ss.keys()) {
            StdOut.println(s + " " + ss.get(s));
        }
    }

}

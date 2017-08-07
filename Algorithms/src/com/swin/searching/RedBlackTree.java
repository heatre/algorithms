package com.swin.searching;

import com.swin.fundamentals.LinkQueue;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * 2-3查找树(2-3 search tree)：一棵空树或者由以下节点组成；2-节点，含有一个键和
 * 两个链接，左链接指向的2-3树中的键都小于该节点，右链接指向的2-3树中的键都大于该节点；
 * 3-节点，含有两个键和三个链接，左链接指向树中的键都小于该节点，中链接指向树中的键位于
 * 该节点两个键之间，右链接指向树中的键都大于该节点
 * 红黑二叉查找树(rea-black binary search tree：红黑二叉树背后的思想用标准的
 * 二叉查找树(完全由2-节点组成)和一些额外的信息(替换3-节点)来表示2-3树，rdbst将
 * 链接分为两种类型，红链接将来那个2-节点连接起来构成一个3-节点，黑链接则是2-3树中的
 * 普通链接
 * 性质：红链接均是左链接；没有一个节点同时和两条红链接相连；该树是完美黑色平衡的，即任意
 * 空链接到根节点的路径上的黑链接数量相同
 * 命题F：在一棵大小为N的2-3树中，查找和插入操作访问的节点必然不超过lgN个
 * 命题G：一棵大小为N的红黑树的高度不会超过2lgN
 * 命题H：一棵大小为N的红黑树，根节点到任意节点的平均路径长度为~1.001lgN
 * 命题I：在一棵红黑树中，以下操作所需时间都是对数级别，get，put，getMax,getMin,floor,
 * ceiling,rank,select,deleteMin,deleteMax,delete,range
 */
public class RedBlackTree<Key extends Comparable<Key>, Val> {
    private Node root;//r-b bst根节点
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * 私有内部类表示bst节点
     */
    private class Node {
        Key key;//键
        Val val;//值
        Node left;//指向左子树链接
        Node right;//指向右子树链接
        int n;//以给节点为根的子树中节点总数
        boolean color; //指向该节点链接颜色

        public Node(Key k, Val v, int n, boolean c) {
            this.key = k;
            this.val = v;
            this.n = n;
            this.color = c;
        }
    }

    /**
     * 二叉查找树构造函数
     */
    public RedBlackTree() {
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
     * 指向该节点的链接是否为红链接
     * @param node
     * @return
     */
    public boolean isRed(Node node){
        if(node == null)
            return false;
        return node.color;
    }

    public void flipColor(Node node){
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    /**
     * 对当前节点右节点进行左旋转,当前节点左链接为红链接
     * @param h 节点
     */
    public Node rotateLeft(Node h){
        if(h == null)
            throw new NullPointerException("first argument is null");
        Node x = h.right;
        h.right = x.left;//旋转操作
        x.left = h;

        x.color = h.color;//改变旋转后指向节点链接颜色
        h.color = RED;

        x.n = h.n;//更新旋转后链接指向的节点的size值；
        h.n = size(h.left)+size(h.right)+1;
        return x;
    }

    /**
     * 对当前节点右节点进行右旋转,当前节点右链接为红链接
     * @param h 节点
     */
    public Node rotateRight(Node h){
        if(h == null)
            throw new NullPointerException("first argument is null");
        Node x = h.left;
        h.left = x.right;//旋转操作
        x.right = h;

        x.color = h.color;//改变旋转后指向节点链接颜色
        h.color = RED;

        x.n = h.n;//更新旋转后链接指向的节点的size值；
        h.n = size(h.left)+size(h.right)+1;
        return x;
    }

    // restore red-black tree invariant
    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColor(h);

        h.n = size(h.left) + size(h.right) + 1;
        return h;
    }
    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColor(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColor(h);
        }
        return h;
    }


    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColor(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColor(h);
        }
        return h;
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
        root.color = BLACK;
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
            return new Node(k, v, 1,RED);
        int cmp = k.compareTo(node.key);
        if (cmp < 0)
            node.left = put(node.left, k, v);
        else if (cmp > 0)
            node.right = put(node.right, k, v);
        else
            node.val = v;
        if(isRed(node.right) && !isRed(node.left))
            rotateLeft(node);
        if(isRed(node.right) && isRed(node.left))
            rotateLeft(node.right);
        if(isRed(node.left) && isRed(node.right))
            flipColor(node);

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
     * @param key 待删除的键
     */
    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null");
        if (!contains(key)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    public Node delete(Node h, Key key) {

        // assert get(h, key) != null;

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    /**
     * 删除最小键
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    public Node deleteMin(Node h) {
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * 删除最大键
     */
    public void deleteMax() {
        if (isEmpty())
            throw new NoSuchElementException("symbol table is empty");
        root = deleteMax(root);
    }

    public Node deleteMax(Node node) {
        if (node.right == null)
            return node.left;
        node.right = deleteMax(node.right);
        node.n = size(node.left) + size(node.right) + 1;
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

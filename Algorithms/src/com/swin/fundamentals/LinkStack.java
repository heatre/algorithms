package com.swin.fundamentals;

import com.swin.utils.Constant;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * LIFO 下压栈（链表实现）
 * Created by Swin on 2016/10/20.
 */
public class LinkStack<Item> implements Iterable<Item> {
    private Node first;//栈顶元素
    private int N;//栈内元素个数

    //节点数据结构
    private class Node {
        private Item item;
        private Node next;
    }

    public LinkStack() {
        first = null;
        N = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
        Node node = new Node();
        node.item = item;
        Node oldNode = first;

        first = node;
        first.next = oldNode;
        N++;
    }

    public Item pop() {
        if(isEmpty())
            return null;
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }

    @Override
    public Iterator iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        String fn = Constant.DATA_DIR + "tobe.txt";
        StdIn.setScanner(Utils.getScanner(fn));
        LinkStack<String> fs = new LinkStack<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (!s.equals("-")) {
                fs.push(s);
            }
        }
        Iterator iterator = fs.iterator();
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }

        while (!fs.isEmpty()) {
            StdOut.print(fs.pop() + " ");
        }

        while (fs.iterator().hasNext()) {
            StdOut.println(fs.pop() + " ");
        }

        StdOut.println("(" + fs.size() + " left on stack)");

    }
}

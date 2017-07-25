package com.swin.fundamentals;

import com.swin.utils.Constant;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * 背包(LIFO）后进先出背包
 * Created by Swin on 2016/10/24.
 */
public class LinkBag<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
    }

    private int size;
    private Node first;

    public LinkBag() {
        this.size = 0;
        this.first = null;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void add(Item item) {
        Node old = first;
        first = new Node();
        first.item = item;
        first.next = old;
        size++;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkBagIterator();
    }

    private class LinkBagIterator implements Iterator<Item> {
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
        LinkBag<String> fs = new LinkBag<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (!s.equals("-")) {
                fs.add(s);
            }
        }
        Iterator iterator = fs.iterator();
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }


        StdOut.println("(" + fs.size() + " left on bag)");

    }
}

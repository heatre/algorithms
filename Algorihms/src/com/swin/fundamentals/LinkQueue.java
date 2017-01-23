package com.swin.fundamentals;

import com.swin.utils.Constant;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * Created by Swin on 2016/10/24.
 */
public class LinkQueue<Item> implements Iterable<Item> {
    private int size ;//队列中元素数量
    private Node first;//最早添加元素
    private Node last;//最近添加元素

    private class Node{
        Item item;
        Node next;
    }

    public LinkQueue(){
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public int size(){
        return size;
    }

    public void enqueue(Item item) {
        Node old = last;//表尾添加元素
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else {
            old.next = last;
            size++;
        }
    }

    public Item dequeue(){
        Item item = first.item;//表头删除元素
        first = first.next;
        if(isEmpty())
            last = null;
        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkQueueIterator();
    }

    private class LinkQueueIterator implements Iterator<Item>{
        private Node current = first;
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return  item;
        }
    }

    public static void main(String[] args) {
        String fn = Constant.DATA_DIR + "tobe.txt";
        StdIn.setScanner(Utils.getScanner(fn));
        LinkQueue<String> fs = new LinkQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (!s.equals("-")) {
                fs.enqueue(s);
            }
        }
        Iterator iterator = fs.iterator();
        while (iterator.hasNext()) {
            StdOut.print(iterator.next() + " ");
        }

        while (!fs.isEmpty()) {
            StdOut.print(fs.dequeue() + " ");
        }

        StdOut.println("(" + fs.size() + " left on queue)");

    }
}

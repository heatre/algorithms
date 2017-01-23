package com.swin.fundamentals;

import com.swin.utils.Constant;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

/**
 * LIFO下压栈，(动态调整数组大小实现)
 * Created by Swin on 2016/10/20.
 */
public class ResizingArrayStack<Item> implements Iterable<Item> {
    private Item[] items;//存储数组
    private int size = 0;//元素个数

    public ResizingArrayStack() {
        items = (Item[]) new Object[1];//默认容量1
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void resize(int max) {
        Item[] items = (Item[]) new Object[max];
        for (int i = 0;i<size;++i){
            items[i] = this.items[i];
        }
        this.items = items;
    }

    public void push(Item item){
        if(size == items.length)
            resize(items.length*2);
        items[size++] = item; //将元素加到栈顶
    }

    public Item pop(){
        if(size == 0)
            return null;
        Item item = items[--size]; //删除栈顶元素
        items[size] = null;//避免对象游离
        if(size >0 && size<items.length/4)
            resize(items.length/2);
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item>{
        private int i = size;
        @Override
        public boolean hasNext() {
            return i>0;
        }

        @Override
        public Item next() {
            return items[--i];
        }
    }

    public static void main(String[] args) {
        String fn = Constant.DATA_DIR + "tobe.txt";
        StdIn.setScanner(Utils.getScanner(fn));
        ResizingArrayStack<String> fs = new ResizingArrayStack<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (!s.equals("-")) {
                fs.push(s);
            }
        }
        Iterator iterator = fs.iterator();
        while (iterator.hasNext()){
            StdOut.print(iterator.next()+" ");
        }
        StdOut.println("(" + fs.size() + " left on stack)");

    }
}

package com.swin.fundamentals;

import com.swin.utils.Constant;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


/**泛型可扩容定容栈
 * Created by Swin on 2016/10/20.
 */
public class FixedCapacityStack<Item> {
    private Item[] items;
    private int cap;//栈的容量
    private int size;//栈内元素个数

    public FixedCapacityStack(int cap) {
        items = (Item[]) new Object[cap];
        this.cap = cap;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isCapacity() {
        return cap == size;
    }

    public int size() {
        return size;
    }

    public void resize(int max){
        Item[] items = (Item[])  new Object[max];
        for(int i = 0; i< size; ++i){
            items[i] = this.items[i];
        }
        this.items = items;
    }

    public void push(Item s) {
        if(size == items.length)
            resize(items.length*2);
        items[size++] = s;
    }

    public Item pop() {
        if (size == 0)
            return null;
        Item item = items[--size];
        items[size] = null;//避免对象游离
        return item;
    }

    public static void main(String[] args) {
        String fn = Constant.DATA_DIR + "tobe.txt";
        StdIn.setScanner(Utils.getScanner(fn));
        FixedCapacityStack<String> fs = new FixedCapacityStack<>(1);
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (!s.equals("-") && !fs.isCapacity()) {
                fs.push(s);
            } else if (!fs.isEmpty()) {
                StdOut.print(fs.pop() + " ");
            }
        }
        StdOut.println("(" + fs.size() + " left on stack)");

    }
}
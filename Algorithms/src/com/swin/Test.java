package com.swin;


import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdOut;


/**
 * Created by Swin on 2016/8/22.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String[] a = Utils.getStringArray("tiny.txt");
        for (String s:a) {
            StdOut.print(s+" ");
        }
        int[] b = new int[]{3,4,5};
        int i = 1;
        System.out.println(b[--i]);
    }
}

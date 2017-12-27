package com.swin.string;

import java.util.Arrays;

/**
 * 给定两个分别由字母组成的字符串 A 和字符串 B，
 * 字符串 B 的长度比字符串 A 短。请问，如何 最
 * 快地判断字符串 B 中所有字母是否都在字符串 A 里?
 */
public class StringContains {


    /**
     * 暴力法 时间复杂度O(m*n),空间复杂度O(1)
     *
     * @param a a.length>b.length
     * @param b
     */
    public static boolean forceContainString(String[] a, String[] b) {
        for (int i = 0; i < b.length; i++) {
            int j;
            for (j = 0; j < a.length; j++) {
                if (b[i] == a[j])
                    break;
            }
            if (j >= a.length)
                return false;
        }
        return true;
    }

    /**
     * 先进行比较在轮询比较a,b
     * 时间复杂度O(mlogm) + O(nlogn)+O(m+n), 空间复杂度O(1)
     * @param a
     * @param b
     * @return
     */
    public static boolean sortContainsString(String[] a, String[] b) {
        Arrays.sort(a);
        Arrays.sort(b);

        for(int i=0,j=0;j<b.length;j++){
            while (i<a.length && (a[i].compareTo(b[j])<0)){
                i++;
            }
            if(i>=a.length || a[i].compareTo(b[j])>0)
                return false;
        }
        return true;
    }

    /**
     * 字符串显示字符串数组
     * @param a
     */
    public static void show(String[] a){
        String str ="";
        for(String s:a){
            str = str+s;
        }
        System.out.println(str);
    }

    /**
     * 测试用例
     *
     * @param args
     */
    public static void main(String[] args) {
        String[] a = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] b = {"a", "b", "c"};
        String[] c = {"a", "b", "c", "s"};


        System.out.println(forceContainString(a,b));
        System.out.println(forceContainString(a,c));

        System.out.println(sortContainsString(a,b));
        System.out.println(sortContainsString(a,c));
    }
}

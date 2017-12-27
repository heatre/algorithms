package com.swin.string;

/**
 * 给定一个字符串，要求把字符串前面的若干个字符移动到字符串的尾部，
 * 如把字符串“abcdef”前 面的 2 个字符'a'和'b'移动到字符串的尾
 * 部，使得原字符串变成字符串“cdefab”。请写一个函数完 成此功能，
 * 要求对长度为 n 的字符串操作的时间复杂度为 O(n)，空间复杂度为 O(1)
 */
public class RotationString {

    /**
     * 将首个字符移动到末尾
     *
     * @param a
     */
    private static void leftShiftOne(String[] a) {
        if (a.length == 0)
            return;
        String t = a[0];
        for (int i = 1; i < a.length; i++) {
            a[i - 1] = a[i];//所有数组字符左移一位
        }
        a[a.length - 1] = t;
    }

    /**
     * 暴力移位法 时间复杂度O(m*n),空间复杂度O(1)
     *
     * @param a 需要移动的字符数组
     * @param m 需要移动到末尾前m个字符
     */
    public static void leftRotateString(String[] a, int m) {

        m %= a.length;//移动的位数大于字符串总长度时

        while (m > 0) {
            leftShiftOne(a);
            m--;
        }
    }


    /**
     * 三步反转法 时间复杂度O(n),空间复杂度O(1)
     * 将一个字符串分成 X 和 Y 两个部分，在每部分字符串上定义反转操作，
     * 如 X^T，即把 X 的所有 字符反转(如，X="abc"，那么 X^T="cba")，
     * 那么就得到下面的结论:(X^TY^T)^T=YX，显然 就解决了字符串的反转问题
     * 例如，字符串 abcdef ，若要让 def 翻转到 abc 的前头，只要按照下述 3 个步骤操作即可:
     * 1. 首先将原字符串分为两个部分，即 X:abc，Y:def;
     * 2. 将 X 反转，X->X^T，即得:abc->cba;将 Y 反转，Y->Y^T，即得:def->fed。
     * 3. 反转上述步骤得到的结果字符串 X^TY^T，即反转字符串 cbafed 的两部分(cba 和 fed)
     * 给予反转，cbafed得到defabc，形式化表示为(X^TY^T)^T=YX，这就实现了整个反转。
     *
     * @param a 需要移动的字符数组
     * @param m 需要移动到末尾前m个字符
     */
    public static void threeStepRotate(String[] a, int m) {
        m %= a.length;//移动位数大于字符串长度时
        reverseString(a, 0, m - 1);
        reverseString(a, m, a.length - 1);
        reverseString(a, 0, a.length - 1);
    }

    /**
     * 反转字符串
     *
     * @param a
     */
    private static void reverseString(String[] a, int lo, int hi) {
        if (a.length == 0 || a.length == 1)
            return;

        while (lo < hi) {
            String t = a[lo];
            a[lo++] = a[hi];
            a[hi--] = t;
        }
    }

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
        leftRotateString(a,4);
        show(a);

        String[] b = {"a", "b", "c", "d", "e", "f", "g", "h"};
        threeStepRotate(b,4);
        show(b);
    }
}

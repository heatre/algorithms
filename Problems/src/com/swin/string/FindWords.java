package com.swin.string;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Problems:
 * Given a dictionary, and a matrix of letters, find all the words in the matrix that are in the dictionary.
 1. When words lie on either straight line in 8 directions(horizontal,vertical or diagonal)?
 2. When words can be matched in all 8 directions at any point without crossing itself?
 3. When words can be matched in all 8 directions at any point, and able to cross itself.
 性能分析：三个问题中都进行for循环的嵌套调用,时间复杂度达到了平方级别，三个问题的空间复杂度都是常数级别
 */
public class FindWords {

    private int rows;//矩阵行数
    private int cols;//矩阵列数
    private int visit[][];//索引是否访问标记数组

    /**
     * 构造函数
     *
     * @param r 矩阵行数
     * @param c 矩阵列数
     */
    public FindWords(int r, int c) {
        this.rows = r;
        this.cols = c;
        visit = new int[r][c];
    }

    /**
     * When words lie on either straight line in 8 directions
     * @param mat 矩阵
     * @param dic 字典
     * @return 包含8个方向的单词总数
     */
    public Set getWordsInStraightLine(String mat[][], List dic) {
        Set<String> words = new HashSet<>();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                getWords1(mat, dic, words, r, c);
            }
        return words;
    }

    /**
     * 根据各个方向返回单词列表
     * @param mat   矩阵
     * @param dic   字典
     * @param words 保存结果单词列表
     * @param row   当前索引行位置
     * @param col   当前索引列位置
     */
    private void getWords1(String mat[][], List dic, Set words, int row, int col) {

        //向上
        String word = "";
        int i = row;
        int j = col;
        while (i > 0) {
            word = word + mat[i--][j];
            if (dic.contains(word))
                words.add(word);
        }

        //向下
        word = "";
        i = row;
        j = col;
        while (i < rows) {
            word = word + mat[i++][j];
            if (dic.contains(word))
                words.add(word);
        }

        //向左
        word = "";
        i = row;
        j = col;
        while (j > 0) {
            word = word + mat[i][j--];
            if (dic.contains(word))
                words.add(word);
        }

        //向右
        word = "";
        i = row;
        j = col;
        while (j < cols) {
            word = word + mat[i][j++];
            if (dic.contains(word))
                words.add(word);
        }

        //向右上
        word = "";
        i = row;
        j = col;
        while (i > 0 && j < cols) {
            word = word + mat[i--][j++];
            if (dic.contains(word))
                words.add(word);
        }

        //向右下
        word = "";
        i = row;
        j = col;
        while (i < rows && j < cols) {
            word = word + mat[i++][j++];
            if (dic.contains(word))
                words.add(word);
        }

        //向左上
        word = "";
        i = row;
        j = col;
        while (i > 0 && j > 0) {
            word = word + mat[i--][j--];
            if (dic.contains(word))
                words.add(word);
        }
        //向左下
        word = "";
        i = row;
        j = col;
        while (i < rows && j < 0) {
            word = word + mat[i++][j--];
            if (dic.contains(word))
                words.add(word);
        }

    }


    /**
     * When words can be matched in all 8 directions at any point without crossing itself
     * 不形成闭环的场景
     * @param mat 矩阵
     * @param dic 字典
     * @return 包含8个方向的单词总数
     */
    public Set getWordsWithoutCrossing(String mat[][], List dic) {
        Set<String> words = new HashSet<>();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                getWords2(mat, dic, words, r, c, "", 5);
            }
        return words;
    }

    /**
     * 递归拼接单词
     * @param mat 矩阵
     * @param dic 字典
     * @param words 单词集合
     * @param row 当前索引行
     * @param col 当前索引列
     * @param w   当前拼接单词
     * @param threshold 阀值，限制拼接单词最长长度
     */
    private void getWords2(String mat[][], List dic, Set words, int row, int col, String w, int threshold) {
        if (w.length() > threshold - 1)
            return;
        if (visit[row][col] == 1)
            return;
        w = w + mat[row][col];
        if (dic.contains(w))
            words.add(w);
        visit[row][col] = 1;
//        System.out.println(w);
        //上、下、左、右 四个方向
        if (row > 0)
            getWords2(mat, dic, words, row - 1, col, w, threshold);
        if (row < rows - 1)
            getWords2(mat, dic, words, row + 1, col, w, threshold);
        if (col > 0)
            getWords2(mat, dic, words, row, col - 1, w, threshold);
        if (col < cols - 1)
            getWords2(mat, dic, words, row, col + 1, w, threshold);

        //左上、右下、右上、左上四个方向
        if (row > 0 && col > 0)
            getWords2(mat, dic, words, row - 1, col - 1, w, threshold);
        if (row < rows - 1 && col < cols - 1)
            getWords2(mat, dic, words, row + 1, col + 1, w, threshold);
        if (row > 0 && col < cols - 1)
            getWords2(mat, dic, words, row - 1, col + 1, w, threshold);
        if (row < rows - 1 && col > 0)
            getWords2(mat, dic, words, row + 1, col - 1, w, threshold);

        visit[row][col] = 0;
    }

    /**
     * When words can be matched in all 8 directions at any point, and able to cross itself.
     * 形成闭环的场景
     * @param mat 矩阵
     * @param dic 字典
     * @return 包含8个方向的单词总数
     */
    public Set getWordsWithCrossing(String mat[][], List dic) {
        Set<String> words = new HashSet<>();
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                getWords3(mat, dic, words, r, c, "", 5);
            }
        return words;
    }

    /**
     * 递归拼接单词
     * @param mat 矩阵
     * @param dic 字典
     * @param words 单词集合
     * @param row 当前索引行
     * @param col 当前索引列
     * @param w   当前拼接单词
     * @param threshold 阀值，限制拼接单词最长长度
     */
    private void getWords3(String mat[][], List dic, Set words, int row, int col, String w, int threshold) {
        if (w.length() > threshold - 1)
            return;
        w = w + mat[row][col];
        if (dic.contains(w))
            words.add(w);
//        System.out.println(w);
        //上、下、左、右 四个方向
        if (row > 0)
            getWords3(mat, dic, words, row - 1, col, w, threshold);
        if (row < rows - 1)
            getWords3(mat, dic, words, row + 1, col, w, threshold);
        if (col > 0)
            getWords3(mat, dic, words, row, col - 1, w, threshold);
        if (col < cols - 1)
            getWords3(mat, dic, words, row, col + 1, w, threshold);

        //左上、右下、右上、左上四个方向
        if (row > 0 && col > 0)
            getWords3(mat, dic, words, row - 1, col - 1, w, threshold);
        if (row < rows - 1 && col < cols - 1)
            getWords3(mat, dic, words, row + 1, col + 1, w, threshold);
        if (row > 0 && col < cols - 1)
            getWords3(mat, dic, words, row - 1, col + 1, w, threshold);
        if (row < rows - 1 && col > 0)
            getWords3(mat, dic, words, row + 1, col - 1, w, threshold);

    }

    /**
     * 测试用例
     *
     * @param args
     */
    public static void main(String[] args) {
        String mat[][] = {
                {"h", "t", "q", "z", "a"},
                {"f", "e", "o", "h", "p"},
                {"o", "m", "l", "m", "b"},
                {"c", "s", "o", "l", "q"},
                {"h", "x", "d", "k", "o"}
        };
        List<String> dic = new ArrayList();
        dic.add("hello");
        dic.add("hfoch");
        dic.add("qza");

        FindWords fw = new FindWords(5, 5);
        Set<String> w1 = fw.getWordsInStraightLine(mat, dic);
        for (String s : w1) {
            System.out.println(s);
        }

        Set<String> w2 = fw.getWordsWithoutCrossing(mat, dic);
        for (String s : w2) {
            System.out.println(s);
        }

        Set<String> w3 = fw.getWordsWithCrossing(mat, dic);
        for (String s : w3) {
            System.out.println(s);
        }

    }

}

package com.swin.fundamentals;

import com.swin.utils.Constant;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Dynamic Connectivity(动态连通性问题)
 * 网络术语:将对象称为触点，将整数对称为连接，将等价类称为连通分量简称为分量
 *(N+3)(N-1)~N^2次数组访问，quick-find算法是平方级别
 * Created by Swin on 2016/12/1.
 */
public class QuickFind {

    private int count;//分量数量
    private int[] id; //分量id,以触点为索引

    public QuickFind(int n) {
        this.count = n;
        id = new int[n];
        //初始分量id
        for (int i = 0; i < n; ++i) {
            id[i] = i;
        }
    }

    public int count() {
        return count;
    }

    private int find(int p) {
        return id[p];
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    private void union(int p, int q) {

        int pId = find(p);
        int qId = find(q);
        if (pId == qId)//p,q在相同的分量当中则直接返回，不需要合并分量
            return;

        for (int i = 0; i < id.length; ++i) {
            if (id[i] == pId) {
                id[i] = qId;//将p所在的分量id合并为q所在分量的id
            }
        }
        count--;//合并分量以后总分量数减1
    }

    public static void main(String[] args) {
        String fn = Constant.DATA_DIR + "tinyUF.txt";
        StdIn.setScanner(Utils.getScanner(fn));
        int n = StdIn.readInt();
        QuickFind uf = new QuickFind(n);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q))
                continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println("count:"+uf.count());
    }

}

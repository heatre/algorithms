package com.swin.fundamentals;

import com.swin.utils.Constant;
import com.swin.utils.Utils;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * 每个触点对应的id[]元素都是同一个分量中的另一个触点的名称，这种联系称为链接
 * 森林的表示：用节点表示触点，用一个节点到另一个节点的箭头表示链接，在这样一个
 * 森林中无论我们从任何触点所对应的节点开始跟随链接，最终都会到达含有该节点的树
 * 的根节点
 * 分析结论：quick union 最坏的情况下是平方级别的，此时输入的整数对是有序的，（0,1）
 * （0,2），（0,3）等，此时处理N对整数对find的操作数组的总次数为2(1+2+3+...+N)~N^2
 * Created by Swin on 2016/12/2.
 */
public class QuickUnion {
    private int[] id;//分量id，触点为索引
    private int count;//分量数量

    public QuickUnion(int n) {
        this.count = n;
        this.id = new int[n];
        for (int i = 0; i < n; i++) {//初始化分量id
            id[i] = i;
        }
    }

    public int count(){
        return count;
    }

    public boolean connected(int p,int q){
        return find(p) == find(q);
    }

    private int find(int v){
        //找出分量名称
        while (v!=id[v]){
            StdOut.println("v:"+v+" id[v]:"+id[v]);
            v = id[v];
            StdOut.println("v:"+v+" id[v]:"+id[v]);
        }
        return v;
    }

    public void union(int p,int q){
        StdOut.println(p+" "+q);
        int pRoot = find(p);
        StdOut.println("pRoot:"+pRoot);
        int qRoot = find(q);
        StdOut.println("qRoot:"+qRoot);
        if(pRoot==qRoot)
            return;
        //将p所对应的根节点分量连接到q所对应的分量
        id[pRoot] = qRoot;
        count--;
    }

    public static void main(String[] args){
        String fn = Constant.DATA_DIR+"tinyUF.txt";
        StdIn.setScanner(Utils.getScanner(fn));
        int n = StdIn.readInt();
        QuickUnion qu = new QuickUnion(n);
        while (!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if(qu.connected(p,q))
                continue;
            qu.union(p,q);
        }
        StdOut.println("count:"+qu.count());
    }
}
